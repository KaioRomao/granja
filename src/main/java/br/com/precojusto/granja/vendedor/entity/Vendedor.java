package br.com.precojusto.granja.vendedor.entity;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serializable;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "vendedor")
public class Vendedor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nm_vendedor", nullable = false)
    private String nmVendedor;
    @CPF
    @Column(name = "ds_cpf", unique = true)
    private String dsCpf;
    @Column(name = "ds_matricula", unique = true)
    private String dsMatricula;

    public Vendedor(String nmVendedor, String dsCpf) {
        this.nmVendedor = nmVendedor;
        this.dsCpf = dsCpf;
        this.dsMatricula = UUID.randomUUID().toString();
    }
}
