package com.samuelclinton.fiaparknotificacoes.domain.event.estacionamento;

import lombok.Data;

@Data
public class EstacionamentoExpirandoEvent {

    private String nomeCondutor;
    private String emailCondutor;
    private String placaVeiculo;

}
