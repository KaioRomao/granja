package br.com.precojusto.granja.venda.entity;

import br.com.precojusto.granja.cliente.entity.Cliente;
import br.com.precojusto.granja.pato.entity.Pato;
import br.com.precojusto.granja.vendedor.entity.Vendedor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "venda")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vendedor_id", nullable = false)
    private Vendedor vendedor;
    @OneToMany(mappedBy = "vendaId", fetch = FetchType.LAZY)
    private List<Pato> patoList = new ArrayList<>();
    @Column(name = "dt_venda", nullable = false, updatable = false)
    private Date dtVenda;
    @Column(name = "vl_total_bruto", nullable = false, precision = 15, scale = 2)
    private BigDecimal vlTotalBruto = BigDecimal.ZERO;
    @Column(name = "vl_desconto", nullable = false, precision = 15, scale = 2)
    private BigDecimal vlDesconto = BigDecimal.ZERO;
    @Column(name = "vl_total_liquido", nullable = false, precision = 15, scale = 2)
    private BigDecimal vlTotalLiquido = BigDecimal.ZERO;
    @Column(name = "st_desconto_aplicado", nullable = false)
    private boolean stDescontoAplicado;

}