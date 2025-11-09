package com.br.pdvpostocombustivel.api.preco;

import com.br.pdvpostocombustivel.api.preco.dto.PrecoRequest;
import com.br.pdvpostocombustivel.api.preco.dto.PrecoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/precos")
@CrossOrigin(origins = "*")
public class PrecoController {

    private final PrecoService precoService;

    public PrecoController(PrecoService precoService) {
        this.precoService = precoService;
    }

    @GetMapping("/all")
    public List<PrecoResponse> listar() {
        return precoService.getAllSemPaginacao();
    }

    @PostMapping
    public ResponseEntity<PrecoResponse> criar(@RequestBody PrecoRequest req) {
        return ResponseEntity.ok(PrecoResponse.fromEntity(precoService.createOrUpdate(req)));
    }

    @GetMapping("/ultimo/{produtoId}")
    public ResponseEntity<PrecoResponse> getUltimoPorProduto(@PathVariable Long produtoId) {
        PrecoResponse response = precoService.getUltimoPorProduto(produtoId);
        if (response == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        precoService.delete(id);
    }
}
