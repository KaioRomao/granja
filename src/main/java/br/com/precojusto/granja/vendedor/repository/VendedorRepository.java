package br.com.precojusto.granja.vendedor.repository;

import br.com.precojusto.granja.vendedor.entity.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Integer> {
    @Query("""
        SELECT v FROM Vendedor v
        WHERE (:id IS NULL OR v.id = :id)
        AND (:nmVendedor IS NULL OR LOWER(v.nmVendedor) LIKE LOWER(CONCAT('%', :nmVendedor, '%')))
        AND (:dsCpf IS NULL OR LOWER(v.dsCpf) LIKE LOWER(CONCAT('%', :dsCpf, '%')))
        AND (:dsMatricula IS NULL OR LOWER(v.dsMatricula) LIKE LOWER(CONCAT('%', :dsMatricula, '%')))
    """)
    List<Vendedor> pesquisar(Integer id, String nmVendedor, String dsCpf, String dsMatricula);

    Vendedor findByDsCpf(String dsCpf);
}
