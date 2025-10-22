package com.br.pdvpostocombustivel.api.custo;

import com.br.pdvpostocombustivel.api.custo.dto.CustoRequest;
import com.br.pdvpostocombustivel.api.custo.dto.CustoResponse;
import com.br.pdvpostocombustivel.domain.entity.Custo;
import com.br.pdvpostocombustivel.domain.repository.CustoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustoService {

    private final CustoRepository repository;

    public CustoService(CustoRepository repository) {
        this.repository = repository;
    }

    public CustoResponse create(CustoRequest req) {
        Custo novoCusto = toEntity(req);
        return toResponse(repository.save(novoCusto));
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

        c.setDescricao(req.descricao());
        c.setValor(req.valor());
        c.setData(req.data());

        return toResponse(repository.save(c));
    }

    public CustoResponse patch(Long id, CustoRequest req) {
        Custo c = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Custo não encontrado. id=" + id));

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

    private Custo toEntity(CustoRequest req) {
        return new Custo(
                req.descricao(),
                req.valor(),
                req.data()
        );
    }

    private CustoResponse toResponse(Custo c) {
        return new CustoResponse(
                c.getId(),
                c.getDescricao(),
                c.getValor(),
                c.getData()
        );
    }
}
