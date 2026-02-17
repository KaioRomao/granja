package br.com.precojusto.granja.venda.dto;

import java.math.BigDecimal;

public record RankingVendedorRS(
        Integer vendedorId,
        String nmVendedor,
        long qtVendas,
        BigDecimal vlTotalVendido
) {}