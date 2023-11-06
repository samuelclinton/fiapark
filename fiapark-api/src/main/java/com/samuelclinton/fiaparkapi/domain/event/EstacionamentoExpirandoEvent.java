package com.samuelclinton.fiaparkapi.domain.event;

import com.samuelclinton.fiaparkapi.domain.model.Estacionamento;
import lombok.Data;

@Data
public class EstacionamentoExpirandoEvent {

    private String nomeCondutor;
    private String emailCondutor;
    private String placaVeiculo;

    public EstacionamentoExpirandoEvent(Estacionamento estacionamento) {
        var condutor = estacionamento.getVeiculo().getCondutor();
        this.nomeCondutor = condutor.getNome();
        this.emailCondutor = condutor.getEmail();
        this.placaVeiculo = estacionamento.getVeiculo().getPlaca();
    }

}
