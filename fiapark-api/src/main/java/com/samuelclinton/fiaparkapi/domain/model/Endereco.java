package com.samuelclinton.fiaparkapi.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class Endereco implements DomainEntity {

    @Schema(example = "Rua das flores")
    private String rua;

    @Schema(example = "423")
    private String numero;

    @Schema(example = "Jardim veranil")
    private String cidade;

    @Schema(example = "SP")
    private String estado;

    @Schema(example = "01234089")
    private String cep;

}
