package com.samuelclinton.fiaparkapi.api.model.input;

import com.samuelclinton.fiaparkapi.domain.data.InputModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EnderecoInput implements InputModel {

    @NotBlank
    @Schema(example = "Rua das flores")
    private String rua;

    @NotBlank
    @Schema(example = "423")
    private String numero;

    @NotBlank
    @Schema(example = "Jardim veranil")
    private String cidade;

    @NotBlank
    @Pattern(regexp = "[A-Z]{2}")
    @Schema(example = "SP")
    private String estado;

    @NotNull
    @Pattern(regexp = "\\d{8}")
    @Schema(example = "01234089")
    private String cep;

}
