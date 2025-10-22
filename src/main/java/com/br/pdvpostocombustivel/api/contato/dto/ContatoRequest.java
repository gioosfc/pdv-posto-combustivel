package com.br.pdvpostocombustivel.api.contato.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ContatoRequest(
        @NotBlank(message = "Nome não pode estar em branco")
        String nome,

        @NotBlank(message = "Email não pode estar em branco")
        @Email(message = "Email deve ser válido")
        String email,

        String telefone
) {
}
