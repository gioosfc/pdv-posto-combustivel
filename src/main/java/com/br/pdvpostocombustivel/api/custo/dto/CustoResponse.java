package com.br.pdvpostocombustivel.api.custo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CustoResponse(
    Long id,
    String descricao,
    BigDecimal valor,
    LocalDate data
) {}
