package com.br.pdvpostocombustivel.api.estoque;

import com.br.pdvpostocombustivel.api.estoque.dto.EstoqueRequest;
import com.br.pdvpostocombustivel.api.estoque.dto.EstoqueResponse;
import com.br.pdvpostocombustivel.domain.entity.Estoque;
import com.br.pdvpostocombustivel.domain.entity.Produto;
import com.br.pdvpostocombustivel.domain.repository.EstoqueRepository;
import com.br.pdvpostocombustivel.domain.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final ProdutoRepository produtoRepository;

    public EstoqueService(EstoqueRepository estoqueRepository, ProdutoRepository produtoRepository) {
        this.estoqueRepository = estoqueRepository;
        this.produtoRepository = produtoRepository;
    }

    public EstoqueResponse create(EstoqueRequest req) {
        Produto produto = produtoRepository.findById(req.produtoId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado. id=" + req.produtoId()));

        estoqueRepository.findByProdutoId(req.produtoId()).ifPresent(e -> {
            throw new IllegalArgumentException("Estoque para este produto já existe. id=" + e.getId());
        });

        Estoque novoEstoque = new Estoque(produto, req.quantidade());
        return toResponse(estoqueRepository.save(novoEstoque));
    }

    @Transactional(readOnly = true)
    public EstoqueResponse getById(Long id) {
        Estoque e = estoqueRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estoque não encontrado. id=" + id));
        return toResponse(e);
    }

    @Transactional(readOnly = true)
    public EstoqueResponse getByProdutoId(Long produtoId) {
        Estoque e = estoqueRepository.findByProdutoId(produtoId)
                .orElseThrow(() -> new IllegalArgumentException("Estoque não encontrado para o produto. id=" + produtoId));
        return toResponse(e);
    }

    @Transactional(readOnly = true)
    public Page<EstoqueResponse> list(int page, int size, String sortBy, Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return estoqueRepository.findAll(pageable).map(this::toResponse);
    }

    public EstoqueResponse update(Long id, EstoqueRequest req) {
        Estoque e = estoqueRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estoque não encontrado. id=" + id));

        Produto produto = produtoRepository.findById(req.produtoId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado. id=" + req.produtoId()));

        if(!e.getProduto().getId().equals(produto.getId())) {
            estoqueRepository.findByProdutoId(req.produtoId()).ifPresent(existing -> {
                throw new IllegalArgumentException("Estoque para este produto já existe. id=" + existing.getId());
            });
        }

        e.setProduto(produto);
        e.setQuantidade(req.quantidade());

        return toResponse(estoqueRepository.save(e));
    }

    public EstoqueResponse patch(Long id, EstoqueRequest req) {
        Estoque e = estoqueRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estoque não encontrado. id=" + id));

        if (req.produtoId() != null) {
            if(!e.getProduto().getId().equals(req.produtoId())) {
                estoqueRepository.findByProdutoId(req.produtoId()).ifPresent(existing -> {
                    throw new IllegalArgumentException("Estoque para este produto já existe. id=" + existing.getId());
                });
                Produto produto = produtoRepository.findById(req.produtoId())
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado. id=" + req.produtoId()));
                e.setProduto(produto);
            }
        }
        if (req.quantidade() != null) {
            e.setQuantidade(req.quantidade());
        }

        return toResponse(estoqueRepository.save(e));
    }

    public void delete(Long id) {
        if (!estoqueRepository.existsById(id)) {
            throw new IllegalArgumentException("Estoque não encontrado. id=" + id);
        }
        estoqueRepository.deleteById(id);
    }

    private EstoqueResponse toResponse(Estoque e) {
        return new EstoqueResponse(
                e.getId(),
                e.getProduto().getId(),
                e.getProduto().getNome(),
                e.getQuantidade()
        );
    }
}
