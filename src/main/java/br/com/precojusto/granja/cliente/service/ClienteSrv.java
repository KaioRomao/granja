package br.com.precojusto.granja.cliente.service;

import br.com.precojusto.granja.cliente.dto.ClienteRQ;
import br.com.precojusto.granja.cliente.dto.ClienteRS;

import java.util.List;

public interface ClienteSrv {
    ClienteRS inserir(ClienteRQ clienteRQ);

    List<ClienteRS> pesquisar(Integer id, String nmPato, boolean stValidoDesconto);

}
