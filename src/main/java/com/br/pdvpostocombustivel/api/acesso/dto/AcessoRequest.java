package com.br.pdvpostocombustivel.api.acesso.dto;

import jakarta.validation.constraints.NotBlank;

public record AcessoRequest(
        Long id,
        String usuario,
        String senha,
        String papel
) {}


