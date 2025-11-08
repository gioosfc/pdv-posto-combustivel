package com.br.pdvpostocombustivel.api.preco.dto;

import com.br.pdvpostocombustivel.domain.entity.Preco;
import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO usado para enviar os dados de Preço ao frontend (Swing)
 * Mantém compatibilidade com o modelo atual do frontend,
 * que espera os campos separados: dataAlteracao e horaAlteracao.
 */
public record PrecoResponse(
        Long id,
        Long produtoId,
        BigDecimal valor,
        Date dataAlteracao,
        Date horaAlteracao,
        String nomeProduto
) {
    public static PrecoResponse fromEntity(Preco p) {
        if (p == null) return null;

        return new PrecoResponse(
                p.getId(),
                (p.getProduto() != null ? p.getProduto().getId() : null),
                p.getValor(),
                p.getDataAlteracao(),
                p.getHoraAlteracao(),
                (p.getProduto() != null ? p.getProduto().getNome() : null)
        );
    }
}
