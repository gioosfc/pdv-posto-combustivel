package com.br.pdvpostocombustivel.domain.repository;

import com.br.pdvpostocombustivel.domain.entity.Estoque;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    Optional<Estoque> findByProdutoId(Long produtoId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select e from Estoque e where e.produto.id = :produtoId")
    Optional<Estoque> findByProdutoIdForUpdate(@Param("produtoId") Long produtoId);
}
