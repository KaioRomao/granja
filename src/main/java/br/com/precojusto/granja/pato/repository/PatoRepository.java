package br.com.precojusto.granja.pato.repository;

import br.com.precojusto.granja.pato.entity.Pato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatoRepository extends JpaRepository<Pato, Integer> {
    @Query("""
        SELECT p FROM Pato p
        LEFT JOIN FETCH p.patoMaeId p_pm
        WHERE (:id IS NULL OR p.id = :id)
        AND (:patoMaeId IS NULL OR p_pm.id = :patoMaeId)
        AND (:nmPato IS NULL OR LOWER(p.nmPato) LIKE LOWER(CONCAT('%', :nmPato, '%')))
    """)
    List<Pato> pesquisar(Integer id, String nmPato, Integer patoMaeId);

    @Query("""
        SELECT p FROM Pato p
        JOIN FETCH p.vendaId p_v
        JOIN FETCH p_v.cliente p_v_c
        WHERE p.vendaId IS NOT NULL
        ORDER BY p_v.dtVenda DESC
    """)
    List<Pato> listarVendidos();

    @Query("""
        SELECT DISTINCT p
        FROM Pato p
        LEFT JOIN FETCH p.patoMaeId p_pm
        LEFT JOIN FETCH p.vendaId p_v
        LEFT JOIN FETCH p_v.cliente p_v_c
        LEFT JOIN FETCH p_v.vendedor p_v_ven
        ORDER BY p.id
    """)
    List<Pato> listarParaRelatorioVenda();
}
