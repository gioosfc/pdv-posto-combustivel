package com.br.pdvpostocombustivel.api.venda.dto;

import java.math.BigDecimal;

public class ResumoProdutoDTO {
    private String produto;
    private BigDecimal litros;
    private BigDecimal total;

    public ResumoProdutoDTO(String produto, BigDecimal litros, BigDecimal total) {
        this.produto = produto;
        this.litros = litros;
        this.total = total;
    }

    public String getProduto() { return produto; }
    public BigDecimal getLitros() { return litros; }
    public BigDecimal getTotal() { return total; }
}
