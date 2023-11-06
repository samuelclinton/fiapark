package com.samuelclinton.fiaparkapi.api.model.output;

import com.samuelclinton.fiaparkapi.domain.data.OutputModel;
import com.samuelclinton.fiaparkapi.domain.model.enums.TipoFormaPagamento;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class FormaPagamentoOutput implements OutputModel {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Visa Black")
    private String apelido;

    private TipoFormaPagamento tipo;

}
