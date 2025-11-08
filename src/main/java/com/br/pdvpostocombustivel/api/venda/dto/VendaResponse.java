package com.br.pdvfrontend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class VendaResponse {
    public Long id;
    public LocalDateTime dataHora;
    public String usuario;
    public String formaPagamento;
    public String placa;
    public BigDecimal total;
    public List<Item> itens;

    public static class Item {
        public Long produtoId;
        public String produtoNome;
        public Long bombaId;
        public String bombaNome;
        public BigDecimal precoUnitario;
        public BigDecimal quantidadeLitros;
        public BigDecimal subtotal;
    }
}
