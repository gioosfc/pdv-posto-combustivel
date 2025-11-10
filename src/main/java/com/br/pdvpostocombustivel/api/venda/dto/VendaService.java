package com.br.pdvpostocombustivel.api.venda.dto;

import com.br.pdvpostocombustivel.api.venda.dto.VendaRequest;
import com.br.pdvpostocombustivel.domain.entity.*;
import com.br.pdvpostocombustivel.domain.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;
    private final PrecoRepository precoRepository;
    private final EstoqueRepository estoqueRepository;

    public VendaService(VendaRepository vendaRepository,
                        ProdutoRepository produtoRepository,
                        PrecoRepository precoRepository,
                        EstoqueRepository estoqueRepository) {
        this.vendaRepository = vendaRepository;
        this.produtoRepository = produtoRepository;
        this.precoRepository = precoRepository;
        this.estoqueRepository = estoqueRepository;
    }

    /** ✅ Cria uma nova venda com base no request */
    @Transactional
    public Venda criarVenda(VendaRequest request) {
        if (request == null || request.getItens() == null || request.getItens().isEmpty()) {
            throw new IllegalArgumentException("A venda deve conter ao menos um item.");
        }

        Venda venda = new Venda();
        venda.setDataHora(new Date());
        venda.setItens(new ArrayList<>());
        venda.setFormaPagamento(request.getFormaPagamento());
        venda.setPlaca(request.getPlaca());

        BigDecimal totalGeral = BigDecimal.ZERO;

        for (VendaRequest.Item itemReq : request.getItens()) {
            Produto produto = produtoRepository.findById(itemReq.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + itemReq.getProdutoId()));

            Preco preco = precoRepository.findTopByProdutoIdOrderByDataAlteracaoDesc(produto.getId());
            if (preco == null || preco.getValor() == null) {
                throw new RuntimeException("Preço inválido ou não encontrado para: " + produto.getNome());
            }

            BigDecimal precoUnitario = preco.getValor();
            BigDecimal quantidade = itemReq.getQuantidade();

            // ======= ATUALIZAÇÃO DE ESTOQUE (com lock) =======
            Estoque estoque = estoqueRepository.findByProdutoIdForUpdate(produto.getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Não há registro de estoque para o produto: " + produto.getNome()));

            if (estoque.getQuantidade().compareTo(quantidade) < 0) {
                throw new EstoqueInsuficienteException(String.format(
                        "Estoque insuficiente para %s. Disponível: %s, solicitado: %s",
                        produto.getNome(), estoque.getQuantidade(), quantidade
                ));
            }

            estoque.setQuantidade(estoque.getQuantidade().subtract(quantidade));
            // Se preferir explicitar o flush aqui:
            // estoqueRepository.save(estoque);
            // ================================================

            BigDecimal subtotal = precoUnitario.multiply(quantidade);

            VendaItem item = new VendaItem();
            item.setProduto(produto);
            item.setQuantidade(quantidade);
            item.setPrecoUnitario(precoUnitario);
            item.setSubtotal(subtotal);
            item.setVenda(venda);
            item.setBombaNome(itemReq.getBombaNome());
            item.setBombaId(itemReq.getBombaId());

            venda.getItens().add(item);
            totalGeral = totalGeral.add(subtotal);
        }

        venda.setTotal(totalGeral);
        return vendaRepository.save(venda);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public class EstoqueInsuficienteException extends RuntimeException {
        public EstoqueInsuficienteException(String msg) { super(msg); }
    }

    /** ✅ Busca uma venda por ID */
    public Venda buscarPorId(Long id) {
        return vendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada com ID: " + id));
    }

    public Venda buscarPorIdComItens(Long id) {
        // Chame o novo método que força o FETCH
        return vendaRepository.findByIdComItens(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada com ID: " + id));
    }

    /** ✅ Lista todas as vendas registradas */
    public List<Venda> listarVendas() {
        return vendaRepository.findAll();
    }

    public List<Venda> relatorio(Date inicio, Date fim, String forma, String placa) {
        String placaPattern = (placa == null || placa.isBlank()) ? null : "%" + placa + "%";
        return vendaRepository.buscarRelatorio(inicio, fim, formaVaziaComoNull(forma), placaPattern);
    }

    public List<ResumoProdutoDTO> resumoProdutos(Date inicio, Date fim, String forma, String placa) {
        String placaPattern = (placa == null || placa.isBlank()) ? null : "%" + placa + "%";
        return vendaRepository.resumoPorProduto(inicio, fim, formaVaziaComoNull(forma), placaPattern);
    }

    private String formaVaziaComoNull(String forma) {
        return (forma == null || forma.isBlank() || "TODAS".equalsIgnoreCase(forma)) ? null : forma;
    }
}