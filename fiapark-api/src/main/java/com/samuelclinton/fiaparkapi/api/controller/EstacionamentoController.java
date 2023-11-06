package com.samuelclinton.fiaparkapi.api.controller;

import com.samuelclinton.fiaparkapi.api.controller.openapi.EstacionamentoControllerOpenApi;
import com.samuelclinton.fiaparkapi.api.model.input.NovoEstacionamentoInput;
import com.samuelclinton.fiaparkapi.api.model.output.EstacionamentoOutput;
import com.samuelclinton.fiaparkapi.domain.exception.NegocioException;
import com.samuelclinton.fiaparkapi.domain.exception.RecursoNaoEncontradoException;
import com.samuelclinton.fiaparkapi.domain.model.Estacionamento;
import com.samuelclinton.fiaparkapi.domain.service.EstacionamentoService;
import com.samuelclinton.fiaparkapi.infrastructure.data.DomainEntityMapperImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/estacionamentos")
public class EstacionamentoController implements EstacionamentoControllerOpenApi {

    @Autowired
    private EstacionamentoService estacionamentoService;

    @Autowired
    private DomainEntityMapperImpl<NovoEstacionamentoInput, EstacionamentoOutput, Estacionamento> estacionamentoMapper;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstacionamentoOutput> estacionar(@Valid @RequestBody NovoEstacionamentoInput novoEstacionamentoInput,
                                        UriComponentsBuilder uriBuilder) {
        try {
            final var novoEstacionamento = estacionamentoService.cadastrar(novoEstacionamentoInput);
            final var estacionamentoOutput = estacionamentoMapper
                    .mapearEntidadeParaOutput(novoEstacionamento, EstacionamentoOutput.class);
            return ResponseEntity
                    .created(uriBuilder.path("/estacionamentos/{estacionamentoId}")
                            .buildAndExpand(novoEstacionamento.getId()).toUri())
                    .body(estacionamentoOutput);
        } catch (RecursoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @Transactional
    @PutMapping("/{estacionamentoId}/finalizacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finalizarEstacionamento(@PathVariable Long estacionamentoId) {
        try {
            final var estacionamento = estacionamentoService.buscar(estacionamentoId);
            estacionamentoService.finalizarEstacionamento(estacionamento);
        } catch (RecursoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

}
