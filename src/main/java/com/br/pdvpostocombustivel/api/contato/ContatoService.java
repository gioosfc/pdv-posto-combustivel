package com.br.pdvpostocombustivel.api.contato;

import com.br.pdvpostocombustivel.api.contato.dto.ContatoRequest;
import com.br.pdvpostocombustivel.api.contato.dto.ContatoResponse;
import com.br.pdvpostocombustivel.domain.entity.Contato;
import com.br.pdvpostocombustivel.domain.repository.ContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContatoService {

    private final ContatoRepository contatoRepository;

    @Autowired
    public ContatoService(ContatoRepository contatoRepository) {
        this.contatoRepository = contatoRepository;
    }

    @Transactional(readOnly = true)
    public List<ContatoResponse> getAllContatos() {
        return contatoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ContatoResponse getContatoById(Long id) {
        return contatoRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Contato não encontrado com id: " + id));
    }

    @Transactional
    public ContatoResponse saveContato(ContatoRequest contatoRequest) {
        Contato contato = new Contato();
        contato.setNome(contatoRequest.nome());
        contato.setEmail(contatoRequest.email());
        contato.setTelefone(contatoRequest.telefone());
        Contato savedContato = contatoRepository.save(contato);
        return toResponse(savedContato);
    }

    @Transactional
    public ContatoResponse updateContato(Long id, ContatoRequest contatoRequest) {
        Contato contato = contatoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contato não encontrado com id: " + id));

        contato.setNome(contatoRequest.nome());
        contato.setEmail(contatoRequest.email());
        contato.setTelefone(contatoRequest.telefone());

        Contato updatedContato = contatoRepository.save(contato);
        return toResponse(updatedContato);
    }

    @Transactional
    public void deleteContato(Long id) {
        if (!contatoRepository.existsById(id)) {
            throw new RuntimeException("Contato não encontrado com id: " + id);
        }
        contatoRepository.deleteById(id);
    }

    private ContatoResponse toResponse(Contato contato) {
        return new ContatoResponse(contato.getId(), contato.getNome(), contato.getEmail(), contato.getTelefone());
    }
}
