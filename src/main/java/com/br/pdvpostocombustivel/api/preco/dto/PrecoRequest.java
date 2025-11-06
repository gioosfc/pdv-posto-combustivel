package com.br.pdvpostocombustivel.api.preco.dto;

import java.math.BigDecimal;
import java.util.Date;

public class PrecoRequest {

    private Long produtoId;
    private BigDecimal valor;

    public Long getProdutoId() { return produtoId; }
    public BigDecimal getValor() { return valor; }
}
