package com.br.pdvpostocombustivel.domain.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "custo")
public class Custo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private BigDecimal valor;

    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    // ---- CONSTRUTORES ----

    public Custo() {}

    public Custo(String descricao, BigDecimal valor, LocalDate data, Produto produto) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.produto = produto;
    }

    // ---- GETTERS E SETTERS ----

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
