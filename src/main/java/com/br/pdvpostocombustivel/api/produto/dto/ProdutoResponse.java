package com.br.pdvpostocombustivel.api.produto.dto;

import com.br.pdvpostocombustivel.domain.entity.Preco;
import com.br.pdvpostocombustivel.domain.entity.Produto;
import java.math.BigDecimal;

public record ProdutoResponse(
        Long id,
        String nome,
        String referencia,
        String marca,
        String categoria,
        String fornecedor,
        BigDecimal custo,
        BigDecimal precoVenda
) {
    public static ProdutoResponse fromEntity(Produto p) {
        return new ProdutoResponse(
                p.getId(),
                p.getNome(),
                p.getReferencia(),
                p.getMarca(),
                p.getCategoria(),
                p.getFornecedor(), // ✅ já é uma String
                null,
                /*p.getPrecoAtual() != null ? p.getCusto().getCustoFixo() : null,*/
                p.getPrecoAtual() != null ? p.getPrecoAtual() : null
        );
    }
}
