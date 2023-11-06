package com.samuelclinton.fiaparkapi.domain.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnderecoEventModel {

    private String rua;
    private String numero;
    private String cidade;
    private String estado;
    private String cep;

}
