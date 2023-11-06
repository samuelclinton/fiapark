package com.samuelclinton.fiaparkapi.api.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FormaPagamentoIdInput {

    @NotNull
    @Schema(example = "1")
    private Long id;

}
