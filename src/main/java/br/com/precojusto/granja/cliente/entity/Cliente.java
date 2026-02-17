package br.com.precojusto.granja.cliente.entity;

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
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "cliente")
public class Cliente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nm_cliente", nullable = false)
    private String nmCliente;
    @Column(name = "st_valido_desconto")
    private boolean stValidoDesconto;

    public Cliente(String nmCliente, boolean stValidoDesconto) {
        this.nmCliente = nmCliente;
        this.stValidoDesconto = stValidoDesconto;
    }
}
