package com.samuelclinton.fiaparkapi.domain.service;

import com.samuelclinton.fiaparkapi.domain.model.Estacionamento;
import com.samuelclinton.fiaparkapi.domain.model.enums.StatusEstacionamento;
import com.samuelclinton.fiaparkapi.domain.model.enums.TipoEstacionamento;
import com.samuelclinton.fiaparkapi.domain.repository.EstacionamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class EstacionamentoExpiradoService {

    @Autowired
    private EstacionamentoRepository estacionamentoRepository;

    @Scheduled(cron = "30 * * ? * ?")
    @Transactional
    public void buscarEstacionamentosExpirando() {
        final var expiracaoProxima = Instant.now().plus(15, ChronoUnit.MINUTES);
        final var estacionamentosExpirando = estacionamentoRepository
                .findByExpiracaoNotificadaIsFalseAndDataTerminoBefore(expiracaoProxima);
        for (Estacionamento estacionamento : estacionamentosExpirando) {
            estacionamento.notificarExpiracao();
        }
        estacionamentoRepository.saveAll(estacionamentosExpirando);
    }

    @Scheduled(cron = "0 * * ? * ?")
    @Transactional
    public void buscarEstacionamentosFinalizados() {
        final var agora = Instant.now();
        final var estacionamentosExpirados = estacionamentoRepository
                .findByTipoAndStatusAndDataTerminoBefore(TipoEstacionamento.FIXO,StatusEstacionamento.INICIADO, agora);
        for (Estacionamento estacionamento : estacionamentosExpirados) {
            estacionamento.finalizar();
        }
        estacionamentoRepository.saveAll(estacionamentosExpirados);
    }

}
