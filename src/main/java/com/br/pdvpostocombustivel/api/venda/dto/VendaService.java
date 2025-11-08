package com.br.pdvpostocombustivel.api.venda.service;

import com.br.pdvpostocombustivel.api.venda.dto.VendaRequest;
import com.br.pdvpostocombustivel.domain.entity.Preco;
import com.br.pdvpostocombustivel.domain.entity.Produto;
import com.br.pdvpostocombustivel.domain.entity.Venda;
import com.br.pdvpostocombustivel.domain.entity.VendaItem;
import com.br.pdvpostocombustivel.domain.repository.PrecoRepository;
import com.br.pdvpostocombustivel.domain.repository.ProdutoRepository;
import com.br.pdvpostocombustivel.domain.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PrecoRepository precoRepository;

    public Venda criarVenda(VendaRequest request) {
        if (request == null || request.getItens() == null || request.getItens().isEmpty()) {
            throw new IllegalArgumentException("A venda deve conter ao menos um item.");
        }

        Venda venda = new Venda();
        venda.setDataVenda(new Date());
        venda.setItens(new ArrayList<>());

        BigDecimal totalGeral = BigDecimal.ZERO;

        for (VendaRequest.Item itemReq : request.getItens()) {

            Produto produto = produtoRepository.findById(itemReq.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + itemReq.getProdutoId()));

            Preco preco = precoRepository.findTopByProdutoIdOrderByDataAlteracaoDesc(produto.getId());

            // ✅ Validação de preço
            if (preco == null || preco.getValor() == null) {
                throw new RuntimeException("Preço inválido ou não encontrado para o produto: " + produto.getNome());
            }

            BigDecimal precoUnitario = preco.getValor();
            BigDecimal quantidade = itemReq.getQuantidade();

            // ✅ Validação de quantidade
            if (quantidade == null) {
                throw new RuntimeException("Quantidade não informada para o produto: " + produto.getNome());
            }

            // ✅ Calcula subtotal com segurança
            BigDecimal subtotal = precoUnitario.multiply(quantidade);

            VendaItem item = new VendaItem();
            item.setProduto(produto);
            item.setQuantidade(quantidade);
            item.setPrecoUnitario(precoUnitario);
            item.setSubtotal(subtotal);
            item.setVenda(venda);

            venda.getItens().add(item);
            totalGeral = totalGeral.add(subtotal);
        }

        venda.setTotal(totalGeral);
        return vendaRepository.save(venda);
    }

    public List<Venda> listarVendas() {
        return vendaRepository.findAll();
    }

    public Venda buscarPorId(Long id) {
        return vendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));
    }
}
