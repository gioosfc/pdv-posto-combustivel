package com.br.pdvpostocombustivel.api.acesso;

import com.br.pdvpostocombustivel.api.acesso.dto.AcessoRequest;
import com.br.pdvpostocombustivel.api.acesso.dto.AcessoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/acessos")
public class AcessoController {

    private final AcessoService service;

    public AcessoController(AcessoService service) {
        this.service = service;
    }

    @PostMapping
    public AcessoResponse salvar(@RequestBody AcessoRequest request) {
        return service.salvar(request);
    }

    @PostMapping("/login")
    public AcessoResponse login(@RequestBody AcessoRequest request) {
        return service.login(request);
    }

    @GetMapping("/all")
    public List<AcessoResponse> listar() {
        return service.listar();
    }

    // outros endpoints...
}

