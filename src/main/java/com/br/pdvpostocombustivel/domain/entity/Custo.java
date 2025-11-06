package com.br.pdvpostocombustivel.domain.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "custo")
public class Custo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double imposto;
    private double custoVariaveis;
    private double margemLucro;
    private double custoFixo;
    private Date dataProcessamento;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    public Long getId() { return id; }
    public double getImposto() { return imposto; }
    public double getCustoVariaveis() { return custoVariaveis; }
    public double getMargemLucro() { return margemLucro; }
    public double getCustoFixo() { return custoFixo; }
    public Date getDataProcessamento() { return dataProcessamento; }

    public Produto getProduto() { return produto; }

    public void setImposto(double imposto) { this.imposto = imposto; }
    public void setCustoVariaveis(double custoVariaveis) { this.custoVariaveis = custoVariaveis; }
    public void setMargemLucro(double margemLucro) { this.margemLucro = margemLucro; }
    public void setCustoFixo(double custoFixo) { this.custoFixo = custoFixo; }
    public void setDataProcessamento(Date dataProcessamento) { this.dataProcessamento = dataProcessamento; }
    public void setProduto(Produto produto) { this.produto = produto; }
}
