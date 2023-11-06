package com.samuelclinton.recibos.api.controller.openapi;

import com.samuelclinton.recibos.api.model.ReciboOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Recibos", description = "Gerencia os recibos")
public interface ReciboControllerOpenApi {

    @Operation(
            summary = "Lista os recibos",
            description = "A listagem pode ou não ser filtrada pelo CPF de um condutor"
    )
    List<ReciboOutput> listar(@Parameter(description = "O CPF de um condutor", example = "93012141057") String cpf);

}
