package com.br.pdvpostocombustivel.api.acesso;

import com.br.pdvpostocombustivel.api.acesso.dto.AcessoRequest;
import com.br.pdvpostocombustivel.api.acesso.dto.AcessoResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/acessos")
@CrossOrigin("*")
public class AcessoController {

    private final AcessoService service;

    public AcessoController(AcessoService service) {
        this.service = service;
    }


    @PostMapping("/login")
    public AcessoResponse login(@RequestBody AcessoRequest request) {
        return service.login(request);
    }

    @GetMapping("/all")
    public List<AcessoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public AcessoResponse buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public AcessoResponse salvar(@RequestBody AcessoRequest request) {
        return service.salvar(request);
    }

    @PutMapping("/{id}")
    public AcessoResponse atualizar(@PathVariable Long id,
                                    @RequestBody AcessoRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
