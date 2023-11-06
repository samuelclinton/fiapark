package com.samuelclinton.fiaparkapi.api.controller.openapi;

import com.samuelclinton.fiaparkapi.api.model.input.NovaFormaPagamentoInput;
import com.samuelclinton.fiaparkapi.api.model.output.FormaPagamentoOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

@Tag(name = "Condutores")
public interface CondutorFormaPagamentoControllerOpenApi {

    @Operation(
            summary = "Cadastra uma forma de pagamento",
            description = "Cadastra uma forma de pagamento vinculada a um condutor"
    )
    ResponseEntity<FormaPagamentoOutput> cadastrar(
            @Parameter(description = "O ID de um condutor", in = ParameterIn.PATH, example = "1") Long condutorId,
            NovaFormaPagamentoInput formaPagamentoInput,
            @Parameter(hidden = true) UriComponentsBuilder uriBuilder);

    @Operation(
            summary = "Exclui uma forma de pagamento",
            description = "Exclui uma forma de pagamento através do ID recebido via URL"
    )
    void excluir(@Parameter(description = "O ID de uma forma de pagamento", in = ParameterIn.PATH, example = "1")
                 Long formaPagamentoId);

}
