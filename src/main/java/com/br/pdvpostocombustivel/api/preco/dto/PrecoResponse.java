package com.br.pdvpostocombustivel.api.preco.dto;

import java.math.BigDecimal;

public record PrecoResponse(
    Long id,
    Long produtoId,
    String produtoNome,
    BigDecimal valor
) {}
