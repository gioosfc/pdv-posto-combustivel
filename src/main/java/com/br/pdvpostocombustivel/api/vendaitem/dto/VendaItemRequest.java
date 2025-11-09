package com.br.pdvfrontend.dto;

import java.math.BigDecimal;

public class VendaItemRequest {

    private Long bombaId;
    private Long produtoId;
    private BigDecimal quantidade;

    public VendaItemRequest(Long bombaId, Long produtoId, BigDecimal quantidade) {
        this.bombaId = bombaId;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
    }

    // Getters e Setters
    public Long getBombaId() {
        return bombaId;
    }

    public void setBombaId(Long bombaId) {
        this.bombaId = bombaId;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }
}
