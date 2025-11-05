package com.br.pdvpostocombustivel.api.estoque.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record EstoqueRequest(
        @NotNull Long produtoId,
        @NotNull BigDecimal quantidade
) {}
