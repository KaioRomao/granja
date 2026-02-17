package br.com.precojusto.granja.venda.dto;

import br.com.precojusto.granja.venda.entity.Venda;

import java.math.BigDecimal;
import java.util.List;

public record VendaRS(
        Integer id,
        java.util.Date dtVenda,
        Cliente cliente,
        Vendedor vendedor,
        List<Pato> patos,
        BigDecimal vlTotalBruto,
        BigDecimal vlDesconto,
        BigDecimal vlTotalLiquido,
        boolean stDescontoAplicado
)  {

    public record Cliente(Integer id,
                          String nmCliente,
                          boolean stValidoDesconto) {}

    public record Vendedor(Integer id,
                           String nmVendedor,
                           String dsCpf,
                           String dsMatricula) {}

    public record Pato(Integer id,
                       String nmPato) {}

    public static VendaRS vendaToVendaRS(Venda venda) {
        return new VendaRS(venda.getId(),
                venda.getDtVenda(),
                new Cliente(
                        venda.getCliente().getId(),
                        venda.getCliente().getNmCliente(),
                        venda.getCliente().isStValidoDesconto()
                ),
                new Vendedor(
                        venda.getVendedor().getId(),
                        venda.getVendedor().getNmVendedor(),
                        venda.getVendedor().getDsCpf(),
                        venda.getVendedor().getDsMatricula()
                ),
                venda.getPatoList().stream()
                        .map(p -> new Pato(p.getId(), p.getNmPato()))
                        .toList(),
                venda.getVlTotalBruto(),
                venda.getVlDesconto(),
                venda.getVlTotalLiquido(),
                venda.isStDescontoAplicado()
        );
    }
}