package com.br.pdvpostocombustivel.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "preco")
public class Preco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    private BigDecimal valor;
    private Date dataAlteracao;
    private Date horaAlteracao;

    public Long getId() { return id; }
    public Produto getProduto() { return produto; }
    public BigDecimal getValor() { return valor; }
    public Date getDataAlteracao() { return dataAlteracao; }
    public Date getHoraAlteracao() { return horaAlteracao; }

    public void setProduto(Produto produto) { this.produto = produto; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public void setDataAlteracao(Date dataAlteracao) { this.dataAlteracao = dataAlteracao; }
    public void setHoraAlteracao(Date horaAlteracao) { this.horaAlteracao = horaAlteracao; }
}

