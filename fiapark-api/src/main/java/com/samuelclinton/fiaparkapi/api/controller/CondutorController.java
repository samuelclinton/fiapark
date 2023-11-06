package com.samuelclinton.fiaparkapi.api.controller;

import com.samuelclinton.fiaparkapi.api.controller.openapi.CondutorControllerOpenApi;
import com.samuelclinton.fiaparkapi.api.model.input.AtualizarUsuarioInput;
import com.samuelclinton.fiaparkapi.api.model.input.NovoUsuarioInput;
import com.samuelclinton.fiaparkapi.api.model.input.UsuarioInput;
import com.samuelclinton.fiaparkapi.api.model.output.CondutorOutput;
import com.samuelclinton.fiaparkapi.api.model.output.CondutorResumoOutput;
import com.samuelclinton.fiaparkapi.domain.model.Condutor;
import com.samuelclinton.fiaparkapi.domain.service.CondutorService;
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
@RequestMapping("/condutores")
public class CondutorController implements CondutorControllerOpenApi {

    @Autowired
    private CondutorService condutorService;

    @Autowired
    private DomainEntityMapperImpl<UsuarioInput, CondutorResumoOutput, Condutor> condutorResumoMapper;

    @Autowired
    private DomainEntityMapperImpl<UsuarioInput, CondutorOutput, Condutor> condutorMapper;

    @Transactional
    @GetMapping(value = "/{condutorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CondutorOutput buscarPorId(@PathVariable Long condutorId) {
        var condutor = condutorService.buscar(condutorId);
        return condutorMapper.mapearEntidadeParaOutput(condutor, CondutorOutput.class);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CondutorOutput> cadastrar(@RequestBody @Valid NovoUsuarioInput novoUsuarioInput,
                                                    UriComponentsBuilder uriBuilder) {
        Condutor condutor = condutorResumoMapper.mapearInputParaEntidade(novoUsuarioInput, Condutor.class);
        condutor = condutorService.cadastrar(condutor);
        var condutorOutput = condutorMapper.mapearEntidadeParaOutput(condutor, CondutorOutput.class);
        return ResponseEntity
                .created(uriBuilder.path("/condutores/{condutorId}").buildAndExpand(condutor.getId()).toUri())
                .body(condutorOutput);
    }

    @PutMapping(value = "/{condutorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CondutorOutput atualizarDados(@PathVariable Long condutorId,
                                   @RequestBody @Valid AtualizarUsuarioInput novoUsuarioInput) {
        Condutor condutorExistente = condutorService.buscar(condutorId);
        Condutor condutorAtualizado = condutorResumoMapper.mapearInputParaEntidade(novoUsuarioInput, Condutor.class);
        condutorExistente = condutorService.atualizar(condutorExistente, condutorAtualizado);
        return condutorMapper.mapearEntidadeParaOutput(condutorExistente, CondutorOutput.class);
    }

    @DeleteMapping(value = "/{condutorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long condutorId) {
        Condutor condutor = condutorService.buscar(condutorId);
        condutorService.excluir(condutor);
    }

}
