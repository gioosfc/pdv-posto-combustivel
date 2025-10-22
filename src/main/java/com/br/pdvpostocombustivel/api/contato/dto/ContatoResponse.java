package com.br.pdvpostocombustivel.api.contato.dto;

public record ContatoResponse(
        Long id,
        String nome,
        String email,
        String telefone
) {
}
