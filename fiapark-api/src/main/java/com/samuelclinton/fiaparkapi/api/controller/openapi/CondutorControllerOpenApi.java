package com.samuelclinton.fiaparkapi.api.controller.openapi;

import com.samuelclinton.fiaparkapi.api.model.input.AtualizarUsuarioInput;
import com.samuelclinton.fiaparkapi.api.model.input.NovoUsuarioInput;
import com.samuelclinton.fiaparkapi.api.model.output.CondutorOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

@Tag(name = "Condutores")
public interface CondutorControllerOpenApi {

    @Operation(
            summary = "Busca um condutor",
            description = "Busca um condutor através do ID recebido por parâmetro de URL"
    )
    CondutorOutput buscarPorId(@Parameter(description = "O ID de um condutor", in = ParameterIn.PATH, example = "1") Long condutorId);

    @Operation(
            summary = "Cadastra um condutor",
            description = "Cadastra um condutor recebendo os dados no corpo da requisição"
    )
    ResponseEntity<CondutorOutput> cadastrar(NovoUsuarioInput novoUsuarioInput,
                                             @Parameter(hidden = true) UriComponentsBuilder uriBuilder);

    @Operation(
            summary = "Atualiza dados de um condutor",
            description = "Atualiza dados de um condutor recebendo os novos dados no corpo da requisição"
    )
    CondutorOutput atualizarDados(
            @Parameter(description = "O ID de um condutor", in = ParameterIn.PATH, example = "1") Long condutorId,
            AtualizarUsuarioInput novoUsuarioInput);

    @Operation(
            summary = "Exclui um condutor",
            description = "Exclui um condutor e todos os seus dados relacionados através do ID recebido"
    )
    void excluir(@Parameter(description = "O ID de um condutor", in = ParameterIn.PATH, example = "1") Long condutorId);

}
