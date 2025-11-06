package com.br.pdvpostocombustivel.api.custo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class CustoRequest {

    @NotNull(message = "Produto obrigatório")
    private Long produtoId;

    @PositiveOrZero(message = "Imposto não pode ser negativo")
    private double imposto;

    @PositiveOrZero(message = "Custos variáveis não podem ser negativos")
    private double custoVariaveis;

    @PositiveOrZero(message = "Margem de lucro não pode ser negativa")
    private double margemLucro;

    @PositiveOrZero(message = "Custo fixo não pode ser negativo")
    private double custoFixo;

    // Construtor vazio (requerido pelo Jackson)
    public CustoRequest() {}

    // GETTERS
    public Long getProdutoId() { return produtoId; }
    public double getImposto() { return imposto; }
    public double getCustoVariaveis() { return custoVariaveis; }
    public double getMargemLucro() { return margemLucro; }
    public double getCustoFixo() { return custoFixo; }

    // SETTERS (Jackson precisa disso)
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }
    public void setImposto(double imposto) { this.imposto = imposto; }
    public void setCustoVariaveis(double custoVariaveis) { this.custoVariaveis = custoVariaveis; }
    public void setMargemLucro(double margemLucro) { this.margemLucro = margemLucro; }
    public void setCustoFixo(double custoFixo) { this.custoFixo = custoFixo; }
}
