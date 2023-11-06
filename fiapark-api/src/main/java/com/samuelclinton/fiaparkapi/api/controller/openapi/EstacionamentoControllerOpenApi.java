package com.samuelclinton.fiaparkapi.api.controller.openapi;

import com.samuelclinton.fiaparkapi.api.model.input.NovoEstacionamentoInput;
import com.samuelclinton.fiaparkapi.api.model.output.EstacionamentoOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

@Tag(name = "Estacionamentos")
public interface EstacionamentoControllerOpenApi {

    @Operation(
            summary = "Inicia um estacionamento",
            description = "Inicia um estacionamento vinculado a um veículo e uma forma de pagamento"
    )
    ResponseEntity<EstacionamentoOutput> estacionar(
            NovoEstacionamentoInput novoEstacionamentoInput,
            @Parameter(hidden = true) UriComponentsBuilder uriBuilder);

    @Operation(
            summary = "Finaliza um estacionamento",
            description = "Finaliza um estacionamento do tipo DINAMICO através do ID recebido via URL"
    )
    void finalizarEstacionamento(
            @Parameter(description = "O ID de um estacionamento", in = ParameterIn.PATH, example = "1")
            Long estacionamentoId);

}
