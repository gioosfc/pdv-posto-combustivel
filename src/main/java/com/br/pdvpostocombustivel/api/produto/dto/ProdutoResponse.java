package com.br.pdvpostocombustivel.api.produto.dto;

public record ProdutoResponse(
    Long id,
    String nome,
    String referencia,
    String marca,
    String categoria,
    String fornecedor
) {}
