package com.samuelclinton.fiaparkapi.domain.service;

import com.samuelclinton.fiaparkapi.api.model.input.NovoEstacionamentoInput;
import com.samuelclinton.fiaparkapi.core.properties.EstacionamentoProperties;
import com.samuelclinton.fiaparkapi.domain.data.OutputModel;
import com.samuelclinton.fiaparkapi.domain.exception.EstacionamentoExistenteException;
import com.samuelclinton.fiaparkapi.domain.exception.EstacionamentoNaoEncontradoException;
import com.samuelclinton.fiaparkapi.domain.exception.NegocioException;
import com.samuelclinton.fiaparkapi.domain.model.Estacionamento;
import com.samuelclinton.fiaparkapi.domain.model.FormaPagamento;
import com.samuelclinton.fiaparkapi.domain.model.Veiculo;
import com.samuelclinton.fiaparkapi.domain.model.enums.StatusEstacionamento;
import com.samuelclinton.fiaparkapi.domain.model.enums.TipoEstacionamento;
import com.samuelclinton.fiaparkapi.domain.repository.EstacionamentoRepository;
import com.samuelclinton.fiaparkapi.infrastructure.data.DomainEntityMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class EstacionamentoService {

    @Autowired
    private EstacionamentoProperties estacionamentoProperties;

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private EstacionamentoRepository estacionamentoRepository;

    @Autowired
    private DomainEntityMapperImpl<NovoEstacionamentoInput, OutputModel, Estacionamento> estacionamentoMapper;

    public Estacionamento buscar(Long estacionamentoId) {
        return estacionamentoRepository.findById(estacionamentoId)
                .orElseThrow(() ->
                        new EstacionamentoNaoEncontradoException(
                                "Nenhum estacionamento encontrado com o id " + estacionamentoId)
                );
    }

    @Transactional
    public Estacionamento cadastrar(NovoEstacionamentoInput input) {

        Veiculo veiculo = veiculoService.buscar(input.getVeiculo().getId());
        FormaPagamento formaPagamento = formaPagamentoService
                .buscar(input.getFormaPagamento().getId());

        final var estacionamento = estacionamentoMapper
                .mapearInputParaEntidade(input, Estacionamento.class);

        verificarDuplicidade(estacionamento);

        if (estacionamento.getTipo().equals(TipoEstacionamento.FIXO)) {
            if (input.getTempoEstacionado() == null) {
                throw new NegocioException("Para estacionamentos do tipo FIXO o campos tempoEstacionado é obrigatório");
            }
            estacionamento.setDataTermino(estacionamento.getDataInicio()
                    .plus(15, ChronoUnit.MINUTES));
        }

        estacionamento.setVeiculo(veiculo);
        estacionamento.setFormaPagamento(formaPagamento);
        estacionamento.setStatus(StatusEstacionamento.INICIADO);
        estacionamento.setTarifa(estacionamentoProperties.getTarifa());

        return estacionamentoRepository.saveAndFlush(estacionamento);
    }

    @Transactional
    public void finalizarEstacionamento(Estacionamento estacionamento) {
        if (abaixoDaTolerancia(estacionamento)) {
            estacionamentoRepository.delete(estacionamento);
        } else if (estacionamento.getTipo().equals(TipoEstacionamento.FIXO)) {
            throw new NegocioException("Estacionamentos do tipo fixo não podem ser finalizados após o período" +
                    " de tolerância, adicione mais tempo após o término do estacionamento caso necessário");
        } else {
            estacionamento.setDataTermino(Instant.now().plus(3, ChronoUnit.HOURS));
            estacionamento.finalizar();
            estacionamentoRepository.save(estacionamento);
        }
    }

    private boolean abaixoDaTolerancia(Estacionamento estacionamento) {
        return false;
//        final var duracaoEstacionamento = Duration.between(estacionamento.getDataInicio(), Instant.now());
//        return duracaoEstacionamento.toMinutes() <= 5;
    }

    private void verificarDuplicidade(Estacionamento estacionamento) {
        final var mensagem = "Já existe um estacionamento iniciado para o veículo com a placa %s";
        estacionamentoRepository
                .findByVeiculoAndStatusIs(estacionamento.getVeiculo(), StatusEstacionamento.INICIADO)
                .ifPresent(estacionamentoExistente -> {
                    throw new EstacionamentoExistenteException(
                            String.format(mensagem, estacionamentoExistente.getVeiculo().getPlaca()));
                });
    }

}
