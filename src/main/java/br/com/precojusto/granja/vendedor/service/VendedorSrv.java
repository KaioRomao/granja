package br.com.precojusto.granja.vendedor.service;

import br.com.precojusto.granja.cliente.dto.ClienteRQ;
import br.com.precojusto.granja.cliente.dto.ClienteRS;
import br.com.precojusto.granja.vendedor.dto.VendedorRQ;
import br.com.precojusto.granja.vendedor.dto.VendedorRS;
import br.com.precojusto.granja.vendedor.entity.Vendedor;

import java.util.List;

public interface VendedorSrv {
    VendedorRS inserir(VendedorRQ vendedorRQ);

    List<VendedorRS> pesquisar(Integer id,
                               String nmVendedor,
                               String dsCpf,
                               String dsMatricula);
    void excluir(Integer id);
}
