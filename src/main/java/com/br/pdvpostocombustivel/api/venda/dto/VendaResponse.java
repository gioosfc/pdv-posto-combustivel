package com.br.pdvfrontend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class VendaResponse {

    private Long id;
    private LocalDateTime dataHora;
    private String formaPagamento;
    private String placa;
    private BigDecimal total;
    private List<Item> itens;

    // ======= Getters e Setters =======
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    // ======= Classe interna Item =======
    public static class Item {
        private Long produtoId;
        private String produtoNome;
        private Long bombaId;
        private String bombaNome;
        private BigDecimal precoUnitario;
        private BigDecimal quantidade;
        private BigDecimal subtotal;

        // Getters e Setters
        public Long getProdutoId() {
            return produtoId;
        }

        public void setProdutoId(Long produtoId) {
            this.produtoId = produtoId;
        }

        public String getProdutoNome() {
            return produtoNome;
        }

        public void setProdutoNome(String produtoNome) {
            this.produtoNome = produtoNome;
        }

        public Long getBombaId() {
            return bombaId;
        }

        public void setBombaId(Long bombaId) {
            this.bombaId = bombaId;
        }

        public String getBombaNome() {
            return bombaNome;
        }

        public void setBombaNome(String bombaNome) {
            this.bombaNome = bombaNome;
        }

        public BigDecimal getPrecoUnitario() {
            return precoUnitario;
        }

        public void setPrecoUnitario(BigDecimal precoUnitario) {
            this.precoUnitario = precoUnitario;
        }

        public BigDecimal getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(BigDecimal quantidade) {
            this.quantidade = quantidade;
        }

        public BigDecimal getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(BigDecimal subtotal) {
            this.subtotal = subtotal;
        }
    }
}
