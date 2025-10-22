package com.br.pdvpostocombustivel.api.produto;

import com.br.pdvpostocombustivel.api.produto.dto.ProdutoRequest;
import com.br.pdvpostocombustivel.api.produto.dto.ProdutoResponse;
import com.br.pdvpostocombustivel.domain.entity.Produto;
import com.br.pdvpostocombustivel.domain.repository.ProdutoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public ProdutoResponse create(ProdutoRequest req) {
        validarUnicidadeReferencia(req.referencia(), null);
        Produto novoProduto = toEntity(req);
        return toResponse(repository.save(novoProduto));
    }

    @Transactional(readOnly = true)
    public ProdutoResponse getById(Long id) {
        Produto p = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado. id=" + id));
        return toResponse(p);
    }

    @Transactional(readOnly = true)
    public ProdutoResponse getByReferencia(String referencia) {
        Produto p = repository.findByReferencia(referencia)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado. referencia=" + referencia));
        return toResponse(p);
    }

    @Transactional(readOnly = true)
    public Page<ProdutoResponse> list(int page, int size, String sortBy, Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return repository.findAll(pageable).map(this::toResponse);
    }

    public ProdutoResponse update(Long id, ProdutoRequest req) {
        Produto p = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado. id=" + id));

        if (req.referencia() != null && !req.referencia().equals(p.getReferencia())) {
            validarUnicidadeReferencia(req.referencia(), id);
        }

        p.setNome(req.nome());
        p.setReferencia(req.referencia());
        p.setMarca(req.marca());
        p.setCategoria(req.categoria());
        p.setFornecedor(req.fornecedor());

        return toResponse(repository.save(p));
    }

    public ProdutoResponse patch(Long id, ProdutoRequest req) {
        Produto p = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado. id=" + id));

        if (req.nome() != null) p.setNome(req.nome());
        if (req.referencia() != null) {
            if (!req.referencia().equals(p.getReferencia())) {
                validarUnicidadeReferencia(req.referencia(), id);
            }
            p.setReferencia(req.referencia());
        }
        if (req.marca() != null) p.setMarca(req.marca());
        if (req.categoria() != null) p.setCategoria(req.categoria());
        if (req.fornecedor() != null) p.setFornecedor(req.fornecedor());

        return toResponse(repository.save(p));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Produto não encontrado. id=" + id);
        }
        repository.deleteById(id);
    }

    private void validarUnicidadeReferencia(String referencia, Long idAtual) {
        repository.findByReferencia(referencia).ifPresent(existente -> {
            if (idAtual == null || !existente.getId().equals(idAtual)) {
                throw new DataIntegrityViolationException("Referência já cadastrada: " + referencia);
            }
        });
    }

    private Produto toEntity(ProdutoRequest req) {
        return new Produto(
                req.nome(),
                req.referencia(),
                req.marca(),
                req.categoria(),
                req.fornecedor()
        );
    }

    private ProdutoResponse toResponse(Produto p) {
        return new ProdutoResponse(
                p.getId(),
                p.getNome(),
                p.getReferencia(),
                p.getMarca(),
                p.getCategoria(),
                p.getFornecedor()
        );
    }
}
