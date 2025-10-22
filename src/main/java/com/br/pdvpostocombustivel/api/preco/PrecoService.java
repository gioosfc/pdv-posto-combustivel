package com.br.pdvpostocombustivel.api.preco;

import com.br.pdvpostocombustivel.api.preco.dto.PrecoRequest;
import com.br.pdvpostocombustivel.api.preco.dto.PrecoResponse;
import com.br.pdvpostocombustivel.domain.entity.Preco;
import com.br.pdvpostocombustivel.domain.entity.Produto;
import com.br.pdvpostocombustivel.domain.repository.PrecoRepository;
import com.br.pdvpostocombustivel.domain.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PrecoService {

    private final PrecoRepository precoRepository;
    private final ProdutoRepository produtoRepository;

    public PrecoService(PrecoRepository precoRepository, ProdutoRepository produtoRepository) {
        this.precoRepository = precoRepository;
        this.produtoRepository = produtoRepository;
    }

    public PrecoResponse create(PrecoRequest req) {
        Produto produto = produtoRepository.findById(req.produtoId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado. id=" + req.produtoId()));

        Preco novoPreco = new Preco(produto, req.valor());
        return toResponse(precoRepository.save(novoPreco));
    }

    @Transactional(readOnly = true)
    public PrecoResponse getById(Long id) {
        Preco p = precoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Preço não encontrado. id=" + id));
        return toResponse(p);
    }

    @Transactional(readOnly = true)
    public List<PrecoResponse> getByProdutoId(Long produtoId) {
        return precoRepository.findByProdutoId(produtoId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<PrecoResponse> list(int page, int size, String sortBy, Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return precoRepository.findAll(pageable).map(this::toResponse);
    }

    public PrecoResponse update(Long id, PrecoRequest req) {
        Preco p = precoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Preço não encontrado. id=" + id));

        Produto produto = produtoRepository.findById(req.produtoId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado. id=" + req.produtoId()));

        p.setProduto(produto);
        p.setValor(req.valor());

        return toResponse(precoRepository.save(p));
    }

    public void delete(Long id) {
        if (!precoRepository.existsById(id)) {
            throw new IllegalArgumentException("Preço não encontrado. id=" + id);
        }
        precoRepository.deleteById(id);
    }

    private PrecoResponse toResponse(Preco p) {
        return new PrecoResponse(
                p.getId(),
                p.getProduto().getId(),
                p.getProduto().getNome(),
                p.getValor()
        );
    }
}
