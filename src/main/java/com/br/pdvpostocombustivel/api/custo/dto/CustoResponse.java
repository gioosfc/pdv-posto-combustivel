package com.br.pdvpostocombustivel.api.custo.dto;

import com.br.pdvpostocombustivel.domain.entity.Custo;

import java.math.BigDecimal;
import java.util.Date;

public class CustoResponse {

    private Long id;
    private Long produtoId;
    private BigDecimal imposto;
    private BigDecimal custoVariaveis;
    private BigDecimal margemLucro;
    private BigDecimal custoFixo;
    private Date dataProcessamento;
    private String nomeProduto;

    public static CustoResponse fromEntity(Custo c) {
        CustoResponse resp = new CustoResponse();

        if (c.getProduto() != null) {
            resp.produtoId = c.getProduto().getId();
            resp.nomeProduto = c.getProduto().getNome();
        }

        resp.id = c.getId();
        resp.imposto = c.getImposto();
        resp.custoVariaveis = c.getCustoVariaveis();
        resp.margemLucro = c.getMargemLucro();
        resp.custoFixo = c.getCustoFixo();
        resp.dataProcessamento = c.getDataProcessamento();

        return resp;
    }

    // GETTERS
    public Long getId() { return id; }
    public Long getProdutoId() { return produtoId; }
    public String getNomeProduto() { return nomeProduto; }
    public BigDecimal getImposto() { return imposto; }
    public BigDecimal getCustoVariaveis() { return custoVariaveis; }
    public BigDecimal getMargemLucro() { return margemLucro; }
    public BigDecimal getCustoFixo() { return custoFixo; }
    public Date getDataProcessamento() { return dataProcessamento; }
}
