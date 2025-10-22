package com.br.pdvpostocombustivel.api.produto.dto;

public record ProdutoRequest(
    String nome,
    String referencia,
    String marca,
    String categoria,
    String fornecedor
) {}
