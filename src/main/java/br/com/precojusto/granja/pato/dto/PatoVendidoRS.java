package br.com.precojusto.granja.pato.dto;

import br.com.precojusto.granja.pato.entity.Pato;

import java.math.BigDecimal;
import java.util.Date;

public record PatoVendidoRS(
        Integer idPato,
        String nmPato,
        Date dtVenda,
        BigDecimal vlTotalLiquidoVenda,
        Cliente cliente
) {
    public record Cliente(Integer id, String nmCliente, boolean stValidoDesconto) {}

    public static PatoVendidoRS patoToPatoVendidoRS(Pato pato) {
        return new PatoVendidoRS(
                pato.getId(),
                pato.getNmPato(),
                pato.getVendaId().getDtVenda(),
                pato.getVendaId().getVlTotalLiquido(),
                new Cliente(
                        pato.getVendaId().getCliente().getId(),
                        pato.getVendaId().getCliente().getNmCliente(),
                        pato.getVendaId().getCliente().isStValidoDesconto()
                )
        );
    }
}