package com.br.pdvpostocombustivel.api.contato.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ContatoRequest(
        String nome,
        String telefone,
        String email,
        String endereco
) {}

