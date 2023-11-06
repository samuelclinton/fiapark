package com.samuelclinton.fiaparkapi.api.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class NovoVeiculoInput implements VeiculoInput {

    @NotNull
    @Pattern(regexp = "([A-Za-z]{3}-\\d{4})|([A-Za-z]{3}\\d[A-Za-z]\\d{2})")
    @Schema(example = "FGT-8798")
    private String placa;

    @NotBlank
    @Schema(example = "Vermelho")
    private String cor;

    @NotBlank
    @Schema(example = "Fiat")
    private String marca;

    @NotBlank
    @Schema(example = "Uno")
    private String modelo;

    @Valid
    @NotNull
    private CondutorIdInput condutor;

}
