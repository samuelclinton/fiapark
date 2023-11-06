package com.samuelclinton.fiaparkapi.api.model.input;

import com.samuelclinton.fiaparkapi.domain.data.InputModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class NovaFormaPagamentoInput implements InputModel {

    @NotNull
    @Pattern(regexp = "(CREDITO)|(DEBITO)")
    @Schema(example = "CREDITO")
    private String tipo;

    @NotBlank
    @Schema(example = "Visa Black")
    private String apelido;

    @NotNull
    @Pattern(regexp = "\\d{16}")
    @Schema(example = "5757700277205060")
    private String cartao;

}
