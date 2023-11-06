package com.samuelclinton.fiaparkapi.api.model.output;

import com.samuelclinton.fiaparkapi.domain.data.OutputModel;
import com.samuelclinton.fiaparkapi.domain.model.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Data
public class CondutorOutput implements OutputModel {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "João das Neves")
    private String nome;

    @Schema(example = "93012141057")
    private String cpf;

    @Schema(example = "joao_das_neves@email.com")
    private String email;

    @Schema(example = "11987895687")
    private String telefone;

    private Endereco endereco;
    private Set<VeiculoResumoOutput> veiculos;
    private Set<FormaPagamentoOutput> formasPagamento;

}
