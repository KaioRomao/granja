package br.com.precojusto.granja.cliente.repository;

import br.com.precojusto.granja.cliente.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    @Query("""
        SELECT c FROM Cliente c
        WHERE (:id IS NULL OR c.id = :id)
        AND (:nmCliente IS NULL OR LOWER(c.nmCliente) LIKE LOWER(CONCAT('%', :nmCliente, '%')))
        AND (c.stValidoDesconto = :stValidoDesconto)
    """)
    List<Cliente> pesquisar(Integer id, String nmCliente, boolean stValidoDesconto);
}
