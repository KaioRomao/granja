package br.com.precojusto.granja.venda.repository;

import br.com.precojusto.granja.venda.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Integer> {
    boolean existsByVendedorId(Integer vendedorId);


    @Query("""
        SELECT
            v_ven.id,
            v_ven.nmVendedor,
            COUNT(DISTINCT v.id),
            COALESCE(SUM(v.vlTotalLiquido), 0)
        FROM Venda v
            JOIN v.vendedor v_ven
            JOIN v.patoList v_pList
        WHERE (:dtInicial IS NULL OR v.dtVenda >= :dtInicial)
          AND (:dtFinal IS NULL OR v.dtVenda <= :dtFinal)
          AND (:stDisponivel IS NULL OR v_pList.stDisponivel = :stDisponivel)
        GROUP BY v_ven.id, v_ven.nmVendedor
    """)
    List<Object[]> rankingVendedores(Date dtInicial, Date dtFinal, Boolean stDisponivel);
}
