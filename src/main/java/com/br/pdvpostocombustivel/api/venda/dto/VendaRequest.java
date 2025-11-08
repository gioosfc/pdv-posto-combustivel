package com.br.pdvfrontend.dto;

import java.util.List;

public class VendaRequest {
    public String usuario;
    public String formaPagamento;
    public String placa;
    public List<VendaItemRequest> itens;

    public VendaRequest(String usuario, String formaPagamento, String placa, List<VendaItemRequest> itens) {
        this.usuario = usuario;
        this.formaPagamento = formaPagamento;
        this.placa = placa;
        this.itens = itens;
    }
}
