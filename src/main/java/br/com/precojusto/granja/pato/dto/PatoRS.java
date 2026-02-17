package br.com.precojusto.granja.pato.dto;

import br.com.precojusto.granja.pato.entity.Pato;

public record PatoRS(Integer id, String nmPato, boolean stDisponivel, Integer patoMaeId, String nmPatoMae, int qtFilhos) {

    public static PatoRS patoToPatoRS(Pato pato) {
        return new PatoRS(
                pato.getId(),
                pato.getNmPato(),
                pato.isStDisponivel(),
                pato.getPatoMaeId() != null ? pato.getPatoMaeId().getId() : null,
                pato.getPatoMaeId() != null ? pato.getPatoMaeId().getNmPato() : null,
                pato.getPatoFilhoList() != null ? pato.getPatoFilhoList().size() : 0
        );
    }
}
