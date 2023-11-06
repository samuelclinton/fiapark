package com.samuelclinton.recibos.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class Veiculo {

    @Schema(example = "FGT-8798")
    private String placa;

    @Schema(example = "Vermelho")
    private String cor;

    @Schema(example = "Fiat")
    private String marca;

    @Schema(example = "Uno")
    private String modelo;

}
