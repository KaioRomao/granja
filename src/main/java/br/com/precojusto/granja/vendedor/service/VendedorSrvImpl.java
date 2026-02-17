package br.com.precojusto.granja.vendedor.service;

import br.com.precojusto.granja.config.exception.GranjaException;
import br.com.precojusto.granja.venda.repository.VendaRepository;
import br.com.precojusto.granja.vendedor.dto.VendedorRQ;
import br.com.precojusto.granja.vendedor.dto.VendedorRS;
import br.com.precojusto.granja.vendedor.entity.Vendedor;
import br.com.precojusto.granja.vendedor.repository.VendedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VendedorSrvImpl implements VendedorSrv {

    private final VendedorRepository vendedorRepository;
    private final VendaRepository vendaRepository;

    @Override
    public VendedorRS inserir(VendedorRQ vendedorRQ) {
        boolean stExisteVendedor = vendedorRepository.findByDsCpf(vendedorRQ.dsCpf()) != null;

        if(stExisteVendedor){
            throw new GranjaException("Já existe um vendedor com esse CPF (" + vendedorRQ.dsCpf()  + "), cadastrado.");
        }
        Vendedor vendedor = vendedorRepository.save(new Vendedor(vendedorRQ.nmVendedor(), vendedorRQ.dsCpf()));
        return VendedorRS.vendedorToVendedorRS(vendedor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VendedorRS> pesquisar(Integer id, String nmVendedor, String dsCpf, String dsMatricula) {
        return vendedorRepository.pesquisar(id, nmVendedor, dsCpf, dsMatricula)
                .stream()
                .map(VendedorRS::vendedorToVendedorRS)
                .toList();
    }

    @Override
    public void excluir(Integer id) {
        Vendedor vendedor = vendedorRepository.findById(id)
                .orElseThrow(() -> new GranjaException("Vendedor não encontrado: " + id));

        boolean stRealizouVenda = vendaRepository.existsByVendedorId(vendedor.getId());
        if (stRealizouVenda) {
            throw new GranjaException("Não é possível excluir vendedor que já realizou vendas.");
        }
        vendedorRepository.delete(vendedor);
    }

}
