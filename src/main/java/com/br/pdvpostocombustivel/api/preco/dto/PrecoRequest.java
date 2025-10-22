package com.br.pdvpostocombustivel.api.preco.dto;

import java.math.BigDecimal;

public record PrecoRequest(
    Long produtoId,
    BigDecimal valor
) {}
