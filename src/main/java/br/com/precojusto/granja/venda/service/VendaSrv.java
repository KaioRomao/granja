package br.com.precojusto.granja.venda.service;

import br.com.precojusto.granja.venda.dto.RankingVendedorRS;
import br.com.precojusto.granja.venda.dto.VendaRQ;
import br.com.precojusto.granja.venda.dto.VendaRS;

import java.time.LocalDate;
import java.util.List;

public interface VendaSrv {
    VendaRS inserir(VendaRQ vendaRQ);

    byte[] gerarRelatorioVendaExcel(LocalDate dtInicial,
                                    LocalDate dtFinal);

    List<RankingVendedorRS> rankingVendedores(LocalDate dtInicial,
                                              LocalDate dtFinal,
                                              String statusVendaPato,
                                              String ordenacao);
}
