package com.br.pdvpostocombustivel.api.contato;

import com.br.pdvpostocombustivel.api.contato.dto.ContatoRequest;
import com.br.pdvpostocombustivel.api.contato.dto.ContatoResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contato")
public class ContatoController {

    private final ContatoService contatoService;

    @Autowired
    public ContatoController(ContatoService contatoService) {
        this.contatoService = contatoService;
    }

    @GetMapping
    public ResponseEntity<List<ContatoResponse>> getAllContatos() {
        return ResponseEntity.ok(contatoService.getAllContatos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContatoResponse> getContatoById(@PathVariable Long id) {
        return ResponseEntity.ok(contatoService.getContatoById(id));
    }

    @PostMapping
    public ResponseEntity<ContatoResponse> saveContato(@RequestBody @Valid ContatoRequest contatoRequest) {
        ContatoResponse savedContato = contatoService.saveContato(contatoRequest);
        return new ResponseEntity<>(savedContato, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContatoResponse> updateContato(@PathVariable Long id, @RequestBody @Valid ContatoRequest contatoRequest) {
        return ResponseEntity.ok(contatoService.updateContato(id, contatoRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContato(@PathVariable Long id) {
        contatoService.deleteContato(id);
        return ResponseEntity.noContent().build();
    }
}
