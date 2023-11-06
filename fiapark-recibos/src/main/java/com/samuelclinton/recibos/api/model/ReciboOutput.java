package com.samuelclinton.recibos.api.model;

import com.samuelclinton.recibos.domain.model.Condutor;
import com.samuelclinton.recibos.domain.model.Recibo;
import com.samuelclinton.recibos.domain.model.TipoFormaPagamento;
import com.samuelclinton.recibos.domain.model.Veiculo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(name = "Recibo")
public class ReciboOutput {

    private Condutor condutor;
    private Veiculo veiculo;
    private TipoFormaPagamento tipoFormaPagamento;

    @Schema(example = "2H")
    private String duracao;

    @Schema(example = "5.0")
    private BigDecimal tarifa;

    @Schema(example = "10.0")
    private BigDecimal valor;

    public ReciboOutput(Recibo recibo) {
        this.condutor = recibo.getCondutor();
        this.veiculo = recibo.getVeiculo();
        this.tipoFormaPagamento = recibo.getTipoFormaPagamento();
        this.duracao = recibo.getDuracao();
        this.tarifa = recibo.getTarifa();
        this.valor = recibo.getValor();
    }

}
