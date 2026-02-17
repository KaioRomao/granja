package br.com.precojusto.granja.pato.service;

import br.com.precojusto.granja.pato.dto.PatoRQ;
import br.com.precojusto.granja.pato.dto.PatoRS;
import br.com.precojusto.granja.pato.dto.PatoVendidoRS;

import java.util.List;

public interface PatoSrv {
    PatoRS inserir(PatoRQ patoRQ);

    List<PatoRS> pesquisar(Integer id, String nmPato, Integer patoMaeId);

    PatoRS buscarPorId(Integer id);

    void desativar(Integer id);

    PatoRS atualizar(Integer id, PatoRQ patoRQ);

    List<PatoVendidoRS> listarPatoVendido();
}
