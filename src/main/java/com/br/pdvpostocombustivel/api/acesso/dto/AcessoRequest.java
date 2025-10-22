package com.br.pdvpostocombustivel.api.acesso.dto;

import jakarta.validation.constraints.NotBlank;

public record AcessoRequest(
        @NotBlank(message = "Login não pode estar em branco")
        String login,
        @NotBlank(message = "Senha não pode estar em branco")
        String senha
) {
}
