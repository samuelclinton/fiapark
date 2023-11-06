package com.samuelclinton.fiaparknotificacoes.domain.event.recibo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ReciboGeradoEvent {

    private Condutor condutor;
    private Veiculo veiculo;
    private TipoFormaPagamento tipoFormaPagamento;
    private String duracao;
    private BigDecimal tarifa;
    private BigDecimal valor;

}
