package com.samuelclinton.fiaparkapi.api.controller;

import com.samuelclinton.fiaparkapi.api.controller.openapi.CondutorFormaPagamentoControllerOpenApi;
import com.samuelclinton.fiaparkapi.api.model.input.NovaFormaPagamentoInput;
import com.samuelclinton.fiaparkapi.api.model.output.FormaPagamentoOutput;
import com.samuelclinton.fiaparkapi.domain.exception.CondutorNaoEncontradoException;
import com.samuelclinton.fiaparkapi.domain.exception.NegocioException;
import com.samuelclinton.fiaparkapi.domain.model.Condutor;
import com.samuelclinton.fiaparkapi.domain.model.FormaPagamento;
import com.samuelclinton.fiaparkapi.domain.service.CondutorService;
import com.samuelclinton.fiaparkapi.domain.service.FormaPagamentoService;
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
@RequestMapping("/condutores/{condutorId}/formas-pagamento")
public class CondutorFormaPagamentoController implements CondutorFormaPagamentoControllerOpenApi {

    @Autowired
    private CondutorService condutorService;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private DomainEntityMapperImpl<NovaFormaPagamentoInput, FormaPagamentoOutput, FormaPagamento> formaPagamentoMapper;

    @Transactional
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormaPagamentoOutput> cadastrar(@PathVariable Long condutorId,
                                                          @RequestBody @Valid NovaFormaPagamentoInput formaPagamentoInput,
                                                          UriComponentsBuilder uriBuilder) {
        try {
            Condutor condutor = condutorService.buscar(condutorId);
            FormaPagamento novaFormaPagamento = formaPagamentoMapper
                    .mapearInputParaEntidade(formaPagamentoInput, FormaPagamento.class);
            novaFormaPagamento.setCondutor(condutor);
            novaFormaPagamento = formaPagamentoService.cadastrar(novaFormaPagamento);
            final var formaPagamentoOutput =
                    formaPagamentoMapper.mapearEntidadeParaOutput(novaFormaPagamento, FormaPagamentoOutput.class);
            return ResponseEntity
                    .created(uriBuilder.path("/condutores/{condutorId}/formas-pagamento/{formaPagamentoId}")
                            .buildAndExpand(condutor.getId(), novaFormaPagamento.getId()).toUri())
                    .body(formaPagamentoOutput);
        } catch (CondutorNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long formaPagamentoId) {
        final var formaPagamento = formaPagamentoService.buscar(formaPagamentoId);
        formaPagamentoService.excluir(formaPagamento);
    }

}
