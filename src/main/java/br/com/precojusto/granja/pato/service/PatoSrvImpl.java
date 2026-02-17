package br.com.precojusto.granja.pato.service;

import br.com.precojusto.granja.config.exception.GranjaException;
import br.com.precojusto.granja.pato.dto.PatoRQ;
import br.com.precojusto.granja.pato.dto.PatoRS;
import br.com.precojusto.granja.pato.dto.PatoVendidoRS;
import br.com.precojusto.granja.pato.entity.Pato;
import br.com.precojusto.granja.pato.repository.PatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PatoSrvImpl implements PatoSrv {

    private final PatoRepository patoRepository;

    @Override
    public PatoRS inserir(PatoRQ patoRQ) {
        Pato pato = new Pato(patoRQ.nmPato(), true);
        if (patoRQ.patoMaeId() != null) {
            Pato patoMae = buscarPatoPorId(patoRQ.patoMaeId());
            pato.setPatoMaeId(patoMae);
        }
        Pato salvo = patoRepository.save(pato);
        return PatoRS.patoToPatoRS(salvo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatoRS> pesquisar(Integer id, String nmPato, Integer patoMaeId) {
        return patoRepository.pesquisar(id, nmPato, patoMaeId)
                .stream()
                .map(PatoRS::patoToPatoRS)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PatoRS buscarPorId(Integer id) {
        return PatoRS.patoToPatoRS(buscarPatoPorId(id));
    }

    @Override
    public void desativar(Integer id) {
        Pato pato = buscarPatoPorId(id);
        if (!pato.isStDisponivel()) {
            throw new GranjaException("Pato já está indisponível.");
        }
        pato.setStDisponivel(false);
        patoRepository.save(pato);
    }

    @Override
    public PatoRS atualizar(Integer id, PatoRQ patoRQ) {
        Pato pato = buscarPatoPorId(id);
        pato.setNmPato(patoRQ.nmPato());
        if (patoRQ.patoMaeId() != null) {
            if (patoRQ.patoMaeId().equals(id)) {
                throw new GranjaException("Um pato não pode ser mãe dele mesmo.");
            }
            Pato patoMae = buscarPatoPorId(patoRQ.patoMaeId());
            pato.setPatoMaeId(patoMae);
        } else {
            pato.setPatoMaeId(null);
        }
        Pato patoAtt = patoRepository.save(pato);
        return PatoRS.patoToPatoRS(patoAtt);
    }

    @Override
    public List<PatoVendidoRS> listarPatoVendido() {
     return patoRepository.listarVendidos()
                           .stream()
                           .map(PatoVendidoRS::patoToPatoVendidoRS)
                           .toList();
    }

    private Pato buscarPatoPorId(Integer id) {
        return patoRepository.findById(id)
                .orElseThrow(() -> new GranjaException("Pato não encontrado: " + id));
    }

}
