package com.br.pdvpostocombustivel.api.preco.dto;

import com.br.pdvpostocombustivel.domain.entity.Preco;

import java.math.BigDecimal;
import java.util.Date;

public class PrecoResponse {

    private Long id;
    private Long produtoId;
    private String nomeProduto;
    private BigDecimal valor;
    private Date dataAlteracao;
    private Date horaAlteracao;

    public static PrecoResponse fromEntity(Preco p) {
        PrecoResponse resp = new PrecoResponse();
        resp.id = p.getId();
        resp.produtoId = (p.getProduto() != null ? p.getProduto().getId() : null);
        resp.nomeProduto = (p.getProduto() != null ? p.getProduto().getNome() : null);
        resp.valor = p.getValor();
        resp.dataAlteracao = p.getDataAlteracao();
        resp.horaAlteracao = p.getHoraAlteracao();
        return resp;
    }

    public Long getId() { return id; }
    public Long getProdutoId() { return produtoId; }
    public String getNomeProduto() { return nomeProduto; }
    public BigDecimal getValor() { return valor; }
    public Date getDataAlteracao() { return dataAlteracao; }
    public Date getHoraAlteracao() { return horaAlteracao; }
}
