package com.br.pdvpostocombustivel.domain.repository;

import com.br.pdvpostocombustivel.domain.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    @Query("SELECT v FROM Venda v LEFT JOIN FETCH v.itens WHERE v.id = :id")
    Optional<Venda> findByIdComItens(@Param("id") Long id);
}
