package com.br.pdvpostocombustivel.api.custo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CustoRequest(
    String descricao,
    BigDecimal valor,
    LocalDate data
) {}
