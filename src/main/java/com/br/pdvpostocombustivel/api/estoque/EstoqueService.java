package com.br.pdvpostocombustivel.api.estoque;

import com.br.pdvpostocombustivel.api.estoque.dto.EstoqueRequest;
import com.br.pdvpostocombustivel.api.estoque.dto.EstoqueResponse;
import com.br.pdvpostocombustivel.domain.entity.Estoque;
import com.br.pdvpostocombustivel.domain.entity.Produto;
import com.br.pdvpostocombustivel.domain.repository.EstoqueRepository;
import com.br.pdvpostocombustivel.domain.repository.ProdutoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        // unicidade: um produto só pode ter um estoque
        estoqueRepository.findByProdutoId(req.produtoId()).ifPresent(e -> {
            throw new DataIntegrityViolationException("Já existe estoque para o produto id=" + req.produtoId());
        });

        Estoque novo = new Estoque(produto, req.quantidade());
        return toResponse(estoqueRepository.save(novo));
    }

    @Transactional(readOnly = true)
    public EstoqueResponse getById(Long id) {
        Estoque e = estoqueRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estoque não encontrado. id=" + id));
        return toResponse(e);
    }

    @Transactional(readOnly = true)
    public List<EstoqueResponse> listAll() {
        return estoqueRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Page<EstoqueResponse> list(int page, int size, String sortBy, Sort.Direction dir) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sortBy));
        return estoqueRepository.findAll(pageable).map(this::toResponse);
    }

    public EstoqueResponse update(Long id, EstoqueRequest req) {
        Estoque e = estoqueRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estoque não encontrado. id=" + id));

        if (!e.getProduto().getId().equals(req.produtoId())) {
            // troca de produto → verificar unicidade
            estoqueRepository.findByProdutoId(req.produtoId()).ifPresent(existente -> {
                throw new DataIntegrityViolationException("Já existe estoque para o produto id=" + req.produtoId());
            });
            Produto novoProduto = produtoRepository.findById(req.produtoId())
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado. id=" + req.produtoId()));
            e.setProduto(novoProduto);
        }

        e.setQuantidade(req.quantidade());
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
                e.getProduto() != null ? e.getProduto().getId() : null,
                e.getProduto() != null ? e.getProduto().getNome() : null,
                e.getProduto() != null ? e.getProduto().getReferencia() : null,
                e.getQuantidade()
        );
    }
}
