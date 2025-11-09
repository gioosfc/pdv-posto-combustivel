package com.br.pdvpostocombustivel.api.preco.dto;

import com.br.pdvpostocombustivel.domain.entity.Preco;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PrecoResponse(
        Long id,
        Long produtoId,
        String nomeProduto,
        BigDecimal valor,
        LocalDateTime dataAlteracao
) {
    public static PrecoResponse fromEntity(Preco p) {
        if (p == null) return null;

        return new PrecoResponse(
                p.getId(),
                p.getProduto() != null ? p.getProduto().getId() : null,
                p.getProduto() != null ? p.getProduto().getNome() : null,
                p.getValor(),
                p.getDataAlteracao()
        );
    }
}
