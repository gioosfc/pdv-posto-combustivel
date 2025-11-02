package com.br.pdvpostocombustivel.api.acesso;

import com.br.pdvpostocombustivel.api.acesso.dto.AcessoRequest;
import com.br.pdvpostocombustivel.api.acesso.dto.AcessoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/acesso")
public class AcessoController {

    private final AcessoService acessoService;

    @Autowired
    public AcessoController(AcessoService acessoService) {
        this.acessoService = acessoService;
    }

    @PostMapping("/login")
    public ResponseEntity<AcessoResponse> login(@RequestBody AcessoRequest request) {
        return ResponseEntity.ok(acessoService.login(request));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AcessoResponse>> listar() {
        return ResponseEntity.ok(acessoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcessoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(acessoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<AcessoResponse> salvar(@RequestBody AcessoRequest request) {
        return ResponseEntity.ok(acessoService.salvar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcessoResponse> atualizar(@PathVariable Long id, @RequestBody AcessoRequest request) {
        return ResponseEntity.ok(acessoService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        acessoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
