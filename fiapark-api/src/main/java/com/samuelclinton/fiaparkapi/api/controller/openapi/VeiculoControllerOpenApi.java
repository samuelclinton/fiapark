package com.samuelclinton.fiaparkapi.api.controller.openapi;

import com.samuelclinton.fiaparkapi.api.model.input.AtualizarVeiculoInput;
import com.samuelclinton.fiaparkapi.api.model.input.NovoVeiculoInput;
import com.samuelclinton.fiaparkapi.api.model.output.VeiculoOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

@Tag(name = "Veículos")
public interface VeiculoControllerOpenApi {

    @Operation(
            summary = "Cadastra um veículo",
            description = "Cadastra um veículo recebendo os dados no corpo da requisição"
    )
    ResponseEntity<VeiculoOutput> cadastrar(NovoVeiculoInput novoVeiculoInput,
                                @Parameter(hidden = true) UriComponentsBuilder uriBuilder);

    @Operation(
            summary = "Atualiza um veículo",
            description = "Atualiza um veículo recebendo os dados no corpo da requisição"
    )
    VeiculoOutput atualizar(
            @Parameter(description = "O ID de um veículo", in = ParameterIn.PATH, example = "1") Long veiculoId,
            AtualizarVeiculoInput atualizarVeiculoInput);

    @Operation(
            summary = "Exclui um veículo",
            description = "Exclui um veículo através do ID recebido via URL"
    )
    void excluir(@Parameter(description = "O ID de um veículo", in = ParameterIn.PATH, example = "1") Long veiculoId);

}
