package com.br.pdvpostocombustivel.domain.repository;

import com.br.pdvpostocombustivel.api.venda.dto.ResumoProdutoDTO;
import com.br.pdvpostocombustivel.domain.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    @Query("SELECT v FROM Venda v LEFT JOIN FETCH v.itens WHERE v.id = :id")
    Optional<Venda> findByIdComItens(@Param("id") Long id);

    @Query("""
       select distinct v
       from Venda v
       left join fetch v.itens i
       where v.dataHora between :inicio and :fim
         and (:forma is null or v.formaPagamento = :forma)
         and (:placaPattern is null or v.placa ilike :placaPattern)
       order by v.dataHora desc
       """)
    List<Venda> buscarRelatorio(@Param("inicio") Date inicio,
                                @Param("fim") Date fim,
                                @Param("forma") String forma,
                                @Param("placaPattern") String placaPattern);

    @Query("""
       select new com.br.pdvpostocombustivel.api.venda.dto.ResumoProdutoDTO(
           i.produto.nome,
           sum(i.quantidade),
           sum(i.subtotal)
       )
       from Venda v
       join v.itens i
       where v.dataHora between :inicio and :fim
         and (:forma is null or v.formaPagamento = :forma)
         and (:placaPattern is null or v.placa ilike :placaPattern)
       group by i.produto.nome
       order by sum(i.subtotal) desc
       """)
    List<ResumoProdutoDTO> resumoPorProduto(@Param("inicio") Date inicio,
                                            @Param("fim") Date fim,
                                            @Param("forma") String forma,
                                            @Param("placaPattern") String placaPattern);
}
