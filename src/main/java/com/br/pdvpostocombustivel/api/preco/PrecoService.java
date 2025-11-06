package com.br.pdvpostocombustivel.api.preco;

import com.br.pdvpostocombustivel.api.preco.dto.PrecoRequest;
import com.br.pdvpostocombustivel.api.preco.dto.PrecoResponse;
import com.br.pdvpostocombustivel.domain.entity.Preco;
import com.br.pdvpostocombustivel.domain.entity.Produto;
import com.br.pdvpostocombustivel.domain.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PrecoService {

    private final com.br.pdvpostocombustivel.api.preco.PrecoRepository repository;
    private final ProdutoRepository produtoRepository;

    public PrecoService(com.br.pdvpostocombustivel.api.preco.PrecoRepository repository, ProdutoRepository produtoRepository) {
        this.repository = repository;
        this.produtoRepository = produtoRepository;
    }

    public List<PrecoResponse> getAllSemPaginacao() {
        return repository.findAll()
                .stream()
                .map(PrecoResponse::fromEntity)
                .toList();
    }

    public Preco createOrUpdate(PrecoRequest req) {

        Produto produto = produtoRepository.findById(req.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Preco preco = repository.findByProdutoId(req.getProdutoId());

        if (preco == null) preco = new Preco();

        preco.setProduto(produto);
        preco.setValor(req.getValor());
        preco.setDataAlteracao(new Date());
        preco.setHoraAlteracao(new Date());

        return repository.save(preco);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
