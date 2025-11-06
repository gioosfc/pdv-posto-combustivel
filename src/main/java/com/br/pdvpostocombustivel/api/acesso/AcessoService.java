package com.br.pdvpostocombustivel.api.acesso;

import com.br.pdvpostocombustivel.api.acesso.dto.AcessoRequest;
import com.br.pdvpostocombustivel.api.acesso.dto.AcessoResponse;
import com.br.pdvpostocombustivel.domain.entity.Acesso;
import com.br.pdvpostocombustivel.domain.repository.AcessoRepository;
import com.br.pdvpostocombustivel.exception.UsuarioJaExisteException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcessoService {

    private final AcessoRepository repository;

    public AcessoService(AcessoRepository repository) {
        this.repository = repository;
    }

    // LOGIN
    public AcessoResponse login(AcessoRequest request) {
        Acesso acesso = repository.findByUsuarioAndSenha(request.usuario(), request.senha())
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));

        return new AcessoResponse(acesso.getId(), acesso.getUsuario(), acesso.getPapel(), acesso.getSenha());
    }

    // LISTAR TODOS
    public List<AcessoResponse> listar() {
        return repository.findAll()
                .stream()
                .map(a -> new AcessoResponse(a.getId(), a.getUsuario(), a.getPapel(), a.getSenha()))
                .toList();
    }

    // BUSCAR POR ID
    public AcessoResponse buscarPorId(Long id) {
        Acesso acesso = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Acesso não encontrado"));

        return new AcessoResponse(acesso.getId(), acesso.getUsuario(), acesso.getPapel(), acesso.getSenha());
    }

    // SALVAR
    public AcessoResponse salvar(AcessoRequest request) {
        if (repository.findByUsuario(request.usuario()).isPresent()) {
            throw new UsuarioJaExisteException("Usuário " + request.usuario() + " já existe.");
        }

        Acesso acesso = new Acesso();
        acesso.setUsuario(request.usuario());
        acesso.setSenha(request.senha());
        acesso.setPapel(request.papel());

        Acesso saved = repository.save(acesso);

        return new AcessoResponse(saved.getId(), saved.getUsuario(), saved.getPapel(), saved.getSenha());
    }

    // ATUALIZAR
    public AcessoResponse atualizar(Long id, AcessoRequest request) {
        Acesso acesso = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Acesso não encontrado"));

        acesso.setUsuario(request.usuario());
        acesso.setSenha(request.senha());
        acesso.setPapel(request.papel());

        Acesso updated = repository.save(acesso);

        return new AcessoResponse(updated.getId(), updated.getUsuario(), updated.getPapel(), updated.getSenha());
    }

    // DELETAR
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
