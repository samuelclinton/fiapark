package com.samuelclinton.fiaparkapi.api.model.output;

import com.samuelclinton.fiaparkapi.domain.data.OutputModel;
import com.samuelclinton.fiaparkapi.domain.model.enums.StatusEstacionamento;
import com.samuelclinton.fiaparkapi.domain.model.enums.TipoEstacionamento;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.Instant;

@Data
public class EstacionamentoOutput implements OutputModel {

    @Schema(example = "1")
    private Long id;

    private VeiculoResumoOutput veiculo;
    private FormaPagamentoOutput formaPagamento;
    private TipoEstacionamento tipo;
    private StatusEstacionamento status;
    private Instant dataInicio;
    private Instant dataTermino;

}
