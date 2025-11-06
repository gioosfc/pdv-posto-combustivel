package com.br.pdvpostocombustivel.api.custo.dto;

import com.br.pdvpostocombustivel.domain.entity.Custo;
import java.util.Date;

public class CustoResponse {

    private Long id;
    private Long produtoId;
    private double imposto;
    private double custoVariaveis;
    private double margemLucro;
    private double custoFixo;
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
    public double getImposto() { return imposto; }
    public double getCustoVariaveis() { return custoVariaveis; }
    public double getMargemLucro() { return margemLucro; }
    public double getCustoFixo() { return custoFixo; }
    public Date getDataProcessamento() { return dataProcessamento; }
}
