package com.br.pdvpostocombustivel.api.custo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class CustoRequest {

    @NotNull(message = "Produto obrigatório")
    private Long produtoId;

    @PositiveOrZero(message = "Imposto não pode ser negativo")
    private BigDecimal imposto;

    @PositiveOrZero(message = "Custos variáveis não podem ser negativos")
    private BigDecimal custoVariaveis;

    @PositiveOrZero(message = "Margem de lucro não pode ser negativa")
    private BigDecimal margemLucro;

    @PositiveOrZero(message = "Custo fixo não pode ser negativo")
    private BigDecimal custoFixo;

    // Construtor vazio (necessário para o Jackson)
    public CustoRequest() {}

    // ✅ Getters
    public Long getProdutoId() { return produtoId; }
    public BigDecimal getImposto() { return imposto; }
    public BigDecimal getCustoVariaveis() { return custoVariaveis; }
    public BigDecimal getMargemLucro() { return margemLucro; }
    public BigDecimal getCustoFixo() { return custoFixo; }

    // ✅ Setters
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }
    public void setImposto(BigDecimal imposto) { this.imposto = imposto; }
    public void setCustoVariaveis(BigDecimal custoVariaveis) { this.custoVariaveis = custoVariaveis; }
    public void setMargemLucro(BigDecimal margemLucro) { this.margemLucro = margemLucro; }
    public void setCustoFixo(BigDecimal custoFixo) { this.custoFixo = custoFixo; }
}
