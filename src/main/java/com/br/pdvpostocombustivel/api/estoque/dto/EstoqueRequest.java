package com.br.pdvpostocombustivel.api.estoque.dto;

import java.math.BigDecimal;

public record EstoqueRequest(
    Long produtoId,
    BigDecimal quantidade
) {}
