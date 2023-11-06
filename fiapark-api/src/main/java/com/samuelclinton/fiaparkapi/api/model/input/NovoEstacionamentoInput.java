package com.samuelclinton.fiaparkapi.api.model.input;

import com.samuelclinton.fiaparkapi.domain.data.InputModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class NovoEstacionamentoInput implements InputModel {

    @Valid
    @NotNull
    private VeiculoIdInput veiculo;

    @Valid
    @NotNull
    private FormaPagamentoIdInput formaPagamento;

    @NotNull
    @Pattern(regexp = "(FIXO)|(DINAMICO)")
    @Schema(example = "FIXO")
    private String tipo;

    @Positive
    @Schema(example = "2")
    private Long tempoEstacionado;

}
