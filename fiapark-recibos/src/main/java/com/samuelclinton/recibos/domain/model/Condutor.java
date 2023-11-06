package com.samuelclinton.recibos.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class Condutor {

    @Schema(example = "João das Neves")
    private String nome;

    @Schema(example = "93012141057")
    private String cpf;

    @Schema(example = "joao_das_neves@email.com")
    private String email;

    @Schema(example = "11987895687")
    private String telefone;

    private Endereco endereco;

}
