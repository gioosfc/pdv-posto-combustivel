package com.br.pdvpostocombustivel.api.preco.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class PrecoRequest {

    @NotNull(message = "Produto obrigatório")
    private Long produtoId;

    @NotNull(message = "Valor obrigatório")
    @PositiveOrZero(message = "Valor não pode ser negativo")
    private BigDecimal valor;

    // ✅ Construtor vazio (necessário para o Jackson)
    public PrecoRequest() {}

    // ✅ Getters
    public Long getProdutoId() {
        return produtoId;
    }

    public BigDecimal getValor() {
        return valor;
    }

    // ✅ Setters
    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
