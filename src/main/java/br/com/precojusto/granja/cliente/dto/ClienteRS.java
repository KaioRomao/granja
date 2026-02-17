package br.com.precojusto.granja.cliente.dto;

import br.com.precojusto.granja.cliente.entity.Cliente;

public record ClienteRS(Integer id,
                        String nmPato,
                        boolean stValidoDesconto) {

    public static ClienteRS clienteToClienteRS(Cliente cliente) {
        return new ClienteRS(
                cliente.getId(),
                cliente.getNmCliente(),
                cliente.isStValidoDesconto()
        );
    }
}
