package com.samuelclinton.fiaparkapi.api.model.output;

import com.samuelclinton.fiaparkapi.domain.data.OutputModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class VeiculoResumoOutput implements OutputModel {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "FGT-8798")
    private String placa;

}
