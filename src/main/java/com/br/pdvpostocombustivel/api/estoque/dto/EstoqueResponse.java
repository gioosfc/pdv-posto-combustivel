package com.br.pdvpostocombustivel.api.estoque.dto;

import java.math.BigDecimal;

public record EstoqueResponse(
    Long id,
    Long produtoId,
    String produtoNome,
    BigDecimal quantidade
) {}
