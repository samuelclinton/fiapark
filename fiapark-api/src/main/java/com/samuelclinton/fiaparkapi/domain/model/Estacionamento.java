package com.samuelclinton.fiaparkapi.domain.model;

import com.samuelclinton.fiaparkapi.domain.event.EstacionamentoExpirandoEvent;
import com.samuelclinton.fiaparkapi.domain.event.EstacionamentoFinalizadoEvent;
import com.samuelclinton.fiaparkapi.domain.model.enums.StatusEstacionamento;
import com.samuelclinton.fiaparkapi.domain.model.enums.TipoEstacionamento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

@Entity
@Table(indexes = { @Index(columnList = "tipo, status, dataTermino"), @Index(columnList = "dataTermino") })
@Getter @Setter
@NoArgsConstructor
public class Estacionamento extends AbstractAggregateRoot<Estacionamento> implements DomainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Veiculo veiculo;

    @ManyToOne
    private FormaPagamento formaPagamento;

    @Enumerated(EnumType.STRING)
    private TipoEstacionamento tipo;

    @Enumerated(EnumType.STRING)
    private StatusEstacionamento status;

    private final Instant dataInicio = Instant.now();

    private Boolean expiracaoNotificada = false;

    private Instant dataTermino;

    private Duration duracao;

    private BigDecimal tarifa;

    private BigDecimal valor;

    public void finalizar() {
        setStatus(StatusEstacionamento.FINALIZADO);
        calcularDuracao();
        calcularValor();
        registerEvent(new EstacionamentoFinalizadoEvent(this));
    }

    public void notificarExpiracao() {
        setExpiracaoNotificada(true);
        registerEvent(new EstacionamentoExpirandoEvent(this));
    }

    public void calcularDuracao() {

        final var duracaoEstacionamento = Duration.between(this.dataInicio, this.dataTermino);

        if (this.tipo.equals(TipoEstacionamento.FIXO)) {

            this.duracao = duracaoEstacionamento;

        } else {

            final int horas = duracaoEstacionamento.toHoursPart();
            final int minutos = duracaoEstacionamento.toMinutesPart();
            final int horaIncompleta = minutos == 0 ? 0 : 1;

            if (horas == 0) {
                this.duracao = Duration.ofHours(1);
            } else {
                this.duracao = Duration.ofHours(horas + horaIncompleta);
            }
        }
    }

    private void calcularValor() {
        this.valor = this.tarifa.multiply(BigDecimal.valueOf(this.duracao.toHours()));
    }

}
