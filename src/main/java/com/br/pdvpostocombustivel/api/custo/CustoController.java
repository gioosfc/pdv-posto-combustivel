package com.br.pdvpostocombustivel.api.custo;

import com.br.pdvpostocombustivel.api.custo.dto.CustoRequest;
import com.br.pdvpostocombustivel.api.custo.dto.CustoResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/custos")
@CrossOrigin(origins = "*") // <- IMPORTANTE para liberar acesso ao frontend Swing
public class CustoController {

    private final CustoService service;

    public CustoController(CustoService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<CustoResponse> getAllSemPaginacao() {
        return service.getAllSemPaginacao();
    }

    @PostMapping
    public CustoResponse salvar(@RequestBody CustoRequest req) {
        return CustoResponse.fromEntity(service.createOrUpdate(req));
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.delete(id);
    }
}
