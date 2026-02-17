package br.com.precojusto.granja.pato.entity;

import br.com.precojusto.granja.venda.entity.Venda;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "pato")
public class Pato implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nm_pato", nullable = false, unique = true, length = 25)
    private String nmPato;
    @Column(name = "st_disponivel")
    private boolean stDisponivel;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pato_mae_id")
    private Pato patoMaeId;
    @OneToMany(mappedBy = "patoMaeId", fetch = FetchType.LAZY)
    private List<Pato> patoFilhoList;
    @ManyToOne
    @JoinColumn(name = "venda_id")
    private Venda vendaId;

    public Pato(String nmPato, boolean stDisponivel) {
        this.nmPato = nmPato;
        this.stDisponivel = stDisponivel;
    }
}
