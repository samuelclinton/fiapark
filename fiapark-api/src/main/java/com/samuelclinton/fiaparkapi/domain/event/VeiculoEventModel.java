package com.samuelclinton.fiaparkapi.domain.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VeiculoEventModel {

    private String placa;
    private String cor;
    private String marca;
    private String modelo;

}
