package com.br.pdvfrontend.dto;

import java.math.BigDecimal;

public class VendaItemRequest {
    public Long bombaId;
    public String bombaNome;
    public Long produtoId;
    public BigDecimal quantidadeLitros;

    public VendaItemRequest(Long bombaId, String bombaNome, Long produtoId, BigDecimal quantidadeLitros) {
        this.bombaId = bombaId;
        this.bombaNome = bombaNome;
        this.produtoId = produtoId;
        this.quantidadeLitros = quantidadeLitros;
    }
}
