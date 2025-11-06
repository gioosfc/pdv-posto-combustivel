package com.br.pdvpostocombustivel.api.preco;

import com.br.pdvpostocombustivel.domain.entity.Preco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrecoRepository extends JpaRepository<Preco, Long> {
    Preco findByProdutoId(Long produtoId);
}
