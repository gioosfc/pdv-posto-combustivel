package com.br.pdvpostocombustivel.api.acesso.dto;

import jakarta.validation.constraints.NotBlank;

public record AcessoRequest(
        @NotBlank(message = "Usuário não pode estar em branco")
        String usuario,
        @NotBlank(message = "Senha não pode estar em branco")
        String senha
) {
}
