package com.samuelclinton.fiaparkapi.api.controller;

import com.samuelclinton.fiaparkapi.api.controller.openapi.CondutorEnderecoControllerOpenApi;
import com.samuelclinton.fiaparkapi.api.model.input.UsuarioInput;
import com.samuelclinton.fiaparkapi.api.model.output.CondutorOutput;
import com.samuelclinton.fiaparkapi.api.model.output.CondutorResumoOutput;
import com.samuelclinton.fiaparkapi.api.model.input.EnderecoInput;
import com.samuelclinton.fiaparkapi.domain.model.Condutor;
import com.samuelclinton.fiaparkapi.domain.model.Endereco;
import com.samuelclinton.fiaparkapi.domain.service.CondutorService;
import com.samuelclinton.fiaparkapi.infrastructure.data.DomainEntityMapperImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/condutores/{condutorId}/enderecos")
public class CondutorEnderecoController implements CondutorEnderecoControllerOpenApi {

    @Autowired
    private CondutorService condutorService;

    @Autowired
    private DomainEntityMapperImpl<EnderecoInput, CondutorResumoOutput, Endereco> enderecoMapper;

    @Autowired
    private DomainEntityMapperImpl<UsuarioInput, CondutorOutput, Condutor> condutorMapper;

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CondutorOutput atualizarEndereco(@PathVariable Long condutorId,
                                            @RequestBody @Valid EnderecoInput enderecoInput) {
        Condutor condutor = condutorService.buscar(condutorId);
        Endereco novoEndereco = enderecoMapper.mapearInputParaEntidade(enderecoInput, Endereco.class);
        condutor = condutorService.atualizarEndereco(condutor, novoEndereco);
        return condutorMapper.mapearEntidadeParaOutput(condutor, CondutorOutput.class);
    }

}
