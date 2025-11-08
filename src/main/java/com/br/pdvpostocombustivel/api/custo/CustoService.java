package com.br.pdvpostocombustivel.api.custo;

import com.br.pdvpostocombustivel.api.custo.dto.CustoRequest;
import com.br.pdvpostocombustivel.api.custo.dto.CustoResponse;
import com.br.pdvpostocombustivel.domain.entity.Custo;
import com.br.pdvpostocombustivel.domain.entity.Produto;
import com.br.pdvpostocombustivel.domain.repository.CustoRepository;
import com.br.pdvpostocombustivel.domain.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import java.util.Date;

import java.util.List;

@Service
public class CustoService {

    private final CustoRepository repository;
    private final ProdutoRepository produtoRepository;

    public CustoService(CustoRepository repository, ProdutoRepository produtoRepository) {
        this.repository = repository;
        this.produtoRepository = produtoRepository;
    }

    public List<CustoResponse> getAllSemPaginacao() {
        return repository.findAll()
                .stream()
                .map(CustoResponse::fromEntity)
                .toList();
    }

    public Custo createOrUpdate(CustoRequest req) {

        Produto produto = produtoRepository.findById(req.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Custo existente = repository.findByProdutoId(req.getProdutoId());

        Custo custo = (existente != null ? existente : new Custo());

        custo.setProduto(produto);
        custo.setImposto(req.getImposto());
        custo.setCustoVariaveis(req.getCustoVariaveis());
        custo.setMargemLucro(req.getMargemLucro());
        custo.setCustoFixo(req.getCustoFixo());
        custo.setDataProcessamento(new Date());

        return repository.save(custo);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
