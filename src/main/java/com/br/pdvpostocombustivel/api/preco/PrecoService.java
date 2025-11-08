package com.br.pdvpostocombustivel.api.preco;

import com.br.pdvpostocombustivel.api.preco.dto.PrecoRequest;
import com.br.pdvpostocombustivel.api.preco.dto.PrecoResponse;
import com.br.pdvpostocombustivel.domain.entity.Preco;
import com.br.pdvpostocombustivel.domain.entity.Produto;
import com.br.pdvpostocombustivel.domain.repository.PrecoRepository;
import com.br.pdvpostocombustivel.domain.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrecoService {

    private final PrecoRepository repository;
    private final ProdutoRepository produtoRepository;

    public PrecoService(PrecoRepository repository, ProdutoRepository produtoRepository) {
        this.repository = repository;
        this.produtoRepository = produtoRepository;
    }

    /** ✅ Retorna lista de PrecoResponse (DTO) */
    public List<PrecoResponse> getAllSemPaginacao() {
        return repository.findAll()
                .stream()
                .map(PrecoResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /** ✅ Cria ou atualiza um preço */
    public Preco createOrUpdate(PrecoRequest req) {

        Produto produto = produtoRepository.findById(req.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Preco preco = repository.findByProdutoId(req.getProdutoId());

        if (preco == null) {
            preco = new Preco();
        }

        preco.setProduto(produto);
        preco.setValor(req.getValor());
        preco.setDataAlteracao(new Date());
        preco.setHoraAlteracao(new Date());

        return repository.save(preco);
    }

    /** ✅ Deleta preço por ID */
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
