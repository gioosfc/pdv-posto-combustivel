package com.br.pdvpostocombustivel.api.preco;

import com.br.pdvpostocombustivel.api.preco.dto.PrecoRequest;
import com.br.pdvpostocombustivel.api.preco.dto.PrecoResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/precos")
public class PrecoController {

    private final PrecoService service;

    public PrecoController(PrecoService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<PrecoResponse> getAllSemPaginacao() {
        return service.getAllSemPaginacao();
    }

    @PostMapping
    public PrecoResponse salvar(@RequestBody PrecoRequest req) {
        return PrecoResponse.fromEntity(service.createOrUpdate(req));
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.delete(id);
    }
}
