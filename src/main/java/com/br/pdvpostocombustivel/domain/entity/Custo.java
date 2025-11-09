package com.br.pdvpostocombustivel.domain.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "custo")
public class Custo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "produto_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Produto produto;

    private BigDecimal imposto;
    private BigDecimal custoVariaveis;
    private BigDecimal margemLucro;
    private BigDecimal custoFixo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataProcessamento;

    public Custo() {}

    // ✅ Construtor opcional
    public Custo(Produto produto, BigDecimal imposto, BigDecimal custoVariaveis,
                 BigDecimal margemLucro, BigDecimal custoFixo, Date dataProcessamento) {
        this.produto = produto;
        this.imposto = imposto;
        this.custoVariaveis = custoVariaveis;
        this.margemLucro = margemLucro;
        this.custoFixo = custoFixo;
        this.dataProcessamento = dataProcessamento;
    }

    // ✅ Getters e Setters
    public Long getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public BigDecimal getImposto() {
        return imposto;
    }

    public void setImposto(BigDecimal imposto) {
        this.imposto = imposto;
    }

    public BigDecimal getCustoVariaveis() {
        return custoVariaveis;
    }

    public void setCustoVariaveis(BigDecimal custoVariaveis) {
        this.custoVariaveis = custoVariaveis;
    }

    public BigDecimal getMargemLucro() {
        return margemLucro;
    }

    public void setMargemLucro(BigDecimal margemLucro) {
        this.margemLucro = margemLucro;
    }

    public BigDecimal getCustoFixo() {
        return custoFixo;
    }

    public void setCustoFixo(BigDecimal custoFixo) {
        this.custoFixo = custoFixo;
    }

    public Date getDataProcessamento() {
        return dataProcessamento;
    }

    public void setDataProcessamento(Date dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }
}
