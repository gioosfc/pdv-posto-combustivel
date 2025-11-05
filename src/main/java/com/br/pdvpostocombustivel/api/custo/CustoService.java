package com.br.pdvpostocombustivel.api.custo;

import com.br.pdvpostocombustivel.api.custo.dto.CustoRequest;
import com.br.pdvpostocombustivel.api.custo.dto.CustoResponse;
import com.br.pdvpostocombustivel.domain.entity.Custo;
import com.br.pdvpostocombustivel.domain.entity.Produto;
import com.br.pdvpostocombustivel.domain.repository.CustoRepository;
import com.br.pdvpostocombustivel.domain.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustoService {

    private final CustoRepository repository;
    private final ProdutoRepository produtoRepository;

    public CustoService(CustoRepository repository, ProdutoRepository produtoRepository) {
        this.repository = repository;
        this.produtoRepository = produtoRepository;
    }

    public CustoResponse create(CustoRequest req) {

        Produto produto = produtoRepository.findById(req.produtoId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado. id=" + req.produtoId()));

        Custo custo = new Custo();
        custo.setProduto(produto);
        custo.setDescricao(req.descricao());
        custo.setValor(req.valor());
        custo.setData(req.data());

        return toResponse(repository.save(custo));
    }

    @Transactional(readOnly = true)
    public CustoResponse getById(Long id) {
        Custo c = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Custo não encontrado. id=" + id));
        return toResponse(c);
    }

    @Transactional(readOnly = true)
    public Page<CustoResponse> list(int page, int size, String sortBy, Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return repository.findAll(pageable).map(this::toResponse);
    }

    public CustoResponse update(Long id, CustoRequest req) {

        Custo c = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Custo não encontrado. id=" + id));

        Produto produto = produtoRepository.findById(req.produtoId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado. id=" + req.produtoId()));

        c.setProduto(produto);
        c.setDescricao(req.descricao());
        c.setData(req.data());
        c.setValor(req.valor());

        return toResponse(repository.save(c));
    }

    public CustoResponse patch(Long id, CustoRequest req) {

        Custo c = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Custo não encontrado. id=" + id));

        if (req.produtoId() != null) {
            Produto produto = produtoRepository.findById(req.produtoId())
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado. id=" + req.produtoId()));
            c.setProduto(produto);
        }

        if (req.descricao() != null) c.setDescricao(req.descricao());
        if (req.valor() != null) c.setValor(req.valor());
        if (req.data() != null) c.setData(req.data());

        return toResponse(repository.save(c));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Custo não encontrado. id=" + id);
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<CustoResponse> getAllSemPaginacao() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private CustoResponse toResponse(Custo c) {
        return new CustoResponse(
                c.getId(),
                c.getDescricao(),
                c.getValor(),
                c.getData(),
                c.getProduto() != null ? c.getProduto().getNome() : null
        );
    }
}
