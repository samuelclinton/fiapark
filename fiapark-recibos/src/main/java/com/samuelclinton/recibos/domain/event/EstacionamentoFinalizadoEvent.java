package com.samuelclinton.recibos.domain.event;

import com.samuelclinton.recibos.domain.model.Condutor;
import com.samuelclinton.recibos.domain.model.TipoFormaPagamento;
import com.samuelclinton.recibos.domain.model.Veiculo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Duration;

@Getter
@Setter
@ToString
public class EstacionamentoFinalizadoEvent {

    private Condutor condutor;
    private Veiculo veiculo;
    private TipoFormaPagamento tipoFormaPagamento;
    private Duration duracao;
    private BigDecimal tarifa;
    private BigDecimal valor;

}
