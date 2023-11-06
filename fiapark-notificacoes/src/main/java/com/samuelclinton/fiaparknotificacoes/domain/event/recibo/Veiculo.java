package com.samuelclinton.fiaparknotificacoes.domain.event.recibo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Veiculo {

    private String placa;
    private String cor;
    private String marca;
    private String modelo;

}
