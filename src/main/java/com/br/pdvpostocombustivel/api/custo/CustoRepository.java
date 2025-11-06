package com.br.pdvpostocombustivel.api.custo;

import com.br.pdvpostocombustivel.domain.entity.Custo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustoRepository extends JpaRepository<Custo, Long> {
    Custo findByProdutoId(Long produtoId);
}
