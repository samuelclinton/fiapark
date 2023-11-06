package com.samuelclinton.recibos.domain.event;

import com.samuelclinton.recibos.domain.model.Condutor;
import com.samuelclinton.recibos.domain.model.Recibo;
import com.samuelclinton.recibos.domain.model.TipoFormaPagamento;
import com.samuelclinton.recibos.domain.model.Veiculo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ReciboGeradoEvent {

    private Condutor condutor;
    private Veiculo veiculo;
    private TipoFormaPagamento tipoFormaPagamento;
    private String duracao;
    private BigDecimal tarifa;
    private BigDecimal valor;

    public ReciboGeradoEvent(Recibo recibo) {
        this.condutor = recibo.getCondutor();
        this.veiculo = recibo.getVeiculo();
        this.tipoFormaPagamento = recibo.getTipoFormaPagamento();
        this.duracao = recibo.getDuracao();
        this.tarifa = recibo.getTarifa();
        this.valor = recibo.getValor();
    }

}
