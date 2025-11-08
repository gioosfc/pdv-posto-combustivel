package com.br.pdvpostocombustivel.api.estoque.dto;

import com.br.pdvpostocombustivel.domain.entity.Estoque;
import java.math.BigDecimal;

public record EstoqueResponse(
        Long id,
        Long produtoId,
        String produtoNome,
        String produtoReferencia,
        BigDecimal quantidade
) {
    public static EstoqueResponse fromEntity(Estoque e) {
        return new EstoqueResponse(
                e.getId(),
                e.getProduto() != null ? e.getProduto().getId() : null,
                e.getProduto() != null ? e.getProduto().getNome() : null,
                e.getProduto() != null ? e.getProduto().getReferencia() : null,
                e.getQuantidade()
        );
    }
}
