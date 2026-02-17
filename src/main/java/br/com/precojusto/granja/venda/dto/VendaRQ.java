package br.com.precojusto.granja.venda.dto;

import java.util.List;

public record VendaRQ(Integer idCliente,
                      Integer idVendedor,
                      List<Integer> idPatoList) {}