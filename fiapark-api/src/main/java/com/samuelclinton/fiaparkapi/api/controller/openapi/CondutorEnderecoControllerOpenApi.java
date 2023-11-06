package com.samuelclinton.fiaparkapi.api.controller.openapi;

import com.samuelclinton.fiaparkapi.api.model.input.EnderecoInput;
import com.samuelclinton.fiaparkapi.api.model.output.CondutorOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Condutores")
public interface CondutorEnderecoControllerOpenApi {

    @Operation(
            summary = "Atualiza o endereço de um condutor",
            description = "Atualiza o endereço de um condutor através do ID recebido por parâmetro de URL e" +
                    " dos dados recebidos no corpo da requisição"
    )
    CondutorOutput atualizarEndereco(
            @Parameter(description = "O ID de um condutor", in = ParameterIn.PATH, example = "1") Long condutorId,
            EnderecoInput enderecoInput);

}
