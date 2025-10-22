package com.br.pdvpostocombustivel.domain.repository;

import com.br.pdvpostocombustivel.domain.entity.Preco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrecoRepository extends JpaRepository<Preco, Long> {
    List<Preco> findByProdutoId(Long produtoId);
}
