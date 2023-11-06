package com.samuelclinton.fiaparkapi.api.controller;

import com.samuelclinton.fiaparkapi.api.controller.openapi.VeiculoControllerOpenApi;
import com.samuelclinton.fiaparkapi.api.model.input.AtualizarVeiculoInput;
import com.samuelclinton.fiaparkapi.api.model.input.NovoVeiculoInput;
import com.samuelclinton.fiaparkapi.api.model.input.VeiculoInput;
import com.samuelclinton.fiaparkapi.api.model.output.VeiculoOutput;
import com.samuelclinton.fiaparkapi.domain.exception.NegocioException;
import com.samuelclinton.fiaparkapi.domain.exception.RecursoNaoEncontradoException;
import com.samuelclinton.fiaparkapi.domain.model.Veiculo;
import com.samuelclinton.fiaparkapi.domain.service.VeiculoService;
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
@RequestMapping("/veiculos")
public class VeiculoController implements VeiculoControllerOpenApi {

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private DomainEntityMapperImpl<VeiculoInput, VeiculoOutput, Veiculo> veiculoMapper;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VeiculoOutput> cadastrar(@RequestBody @Valid NovoVeiculoInput novoVeiculoInput,
                                       UriComponentsBuilder uriBuilder) {
        final var novoVeiculo = veiculoMapper.mapearInputParaEntidade(novoVeiculoInput, Veiculo.class);
        try {
            final var veiculo = veiculoService.cadastrar(novoVeiculo);
            return ResponseEntity
                    .created(uriBuilder.path("/veiculos/{veiculoId}")
                            .buildAndExpand(veiculo.getId()).toUri())
                    .body(veiculoMapper.mapearEntidadeParaOutput(veiculo, VeiculoOutput.class));
        } catch (RecursoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @Transactional
    @PutMapping(value = "/{veiculoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public VeiculoOutput atualizar(@PathVariable Long veiculoId,
                                   @RequestBody @Valid AtualizarVeiculoInput atualizarVeiculoInput) {
        Veiculo veiculoExistente = veiculoService.buscar(veiculoId);
        Veiculo veiculoAtualizado = veiculoMapper.mapearInputParaEntidade(atualizarVeiculoInput, Veiculo.class);
        veiculoExistente = veiculoService.atualizar(veiculoExistente, veiculoAtualizado);
        return veiculoMapper.mapearEntidadeParaOutput(veiculoExistente, VeiculoOutput.class);
    }

    @DeleteMapping("/{veiculoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long veiculoId) {
        final var veiculo = veiculoService.buscar(veiculoId);
        veiculoService.excluir(veiculo);
    }

}
