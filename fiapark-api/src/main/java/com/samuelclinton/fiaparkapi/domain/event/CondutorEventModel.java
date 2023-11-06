package com.samuelclinton.fiaparkapi.domain.event;

import com.samuelclinton.fiaparkapi.domain.data.OutputModel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CondutorEventModel implements OutputModel {

    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private EnderecoEventModel endereco;

}
