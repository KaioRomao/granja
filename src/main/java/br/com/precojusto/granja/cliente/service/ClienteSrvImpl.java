package br.com.precojusto.granja.cliente.service;

import br.com.precojusto.granja.cliente.dto.ClienteRQ;
import br.com.precojusto.granja.cliente.dto.ClienteRS;
import br.com.precojusto.granja.cliente.entity.Cliente;
import br.com.precojusto.granja.cliente.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClienteSrvImpl implements ClienteSrv {

    private final ClienteRepository clienteRepository;

    @Override
    public ClienteRS inserir(ClienteRQ clienteRQ) {
        Cliente cliente = clienteRepository.save(new Cliente(clienteRQ.nmCliente(),
                                                             clienteRQ.stValidoDesconto()));
        return ClienteRS.clienteToClienteRS(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteRS> pesquisar(Integer id, String nmCliente, boolean stValidoDesconto) {
        return clienteRepository.pesquisar(id, nmCliente, stValidoDesconto)
                .stream()
                .map(ClienteRS::clienteToClienteRS)
                .toList();
    }

}
