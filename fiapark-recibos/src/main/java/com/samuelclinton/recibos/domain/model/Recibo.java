package com.samuelclinton.recibos.domain.model;

import com.samuelclinton.recibos.domain.event.EstacionamentoFinalizadoEvent;
import com.samuelclinton.recibos.domain.event.ReciboGeradoEvent;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.math.BigDecimal;
import java.time.Duration;

@Entity
@Table(indexes = @Index(columnList = "cpf"))
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Getter
@Setter
@NoArgsConstructor
public class Recibo extends AbstractAggregateRoot<Recibo> {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Condutor condutor;
    private Veiculo veiculo;
    private TipoFormaPagamento tipoFormaPagamento;
    private String duracao;
    private BigDecimal tarifa;
    private BigDecimal valor;

    private String formatarTempoEstacionado(Duration tempoEstacionado) {
        return tempoEstacionado.toString()
                .substring(2)
                .replaceAll("(\\d[HMS])(?!$)", "$1 ")
                .toLowerCase();
    }

    public Recibo(EstacionamentoFinalizadoEvent estacionamentoFinalizadoEvent) {
        this.condutor = estacionamentoFinalizadoEvent.getCondutor();
        this.veiculo = estacionamentoFinalizadoEvent.getVeiculo();
        this.tipoFormaPagamento = estacionamentoFinalizadoEvent.getTipoFormaPagamento();
        this.duracao = formatarTempoEstacionado(estacionamentoFinalizadoEvent.getDuracao());
        this.tarifa = estacionamentoFinalizadoEvent.getTarifa();
        this.valor = estacionamentoFinalizadoEvent.getValor();
        registerEvent(new ReciboGeradoEvent(this));
    }

}
