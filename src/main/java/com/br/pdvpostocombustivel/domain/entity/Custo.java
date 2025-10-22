package com.br.pdvpostocombustivel.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "custos")
public class Custo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @NotNull
    @Column(name = "data", nullable = false)
    private LocalDate data;

    public Custo() {}

    public Custo(String descricao, BigDecimal valor, LocalDate data) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
