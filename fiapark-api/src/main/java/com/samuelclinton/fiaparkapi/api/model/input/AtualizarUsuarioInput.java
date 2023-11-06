package com.samuelclinton.fiaparkapi.api.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Data
public class AtualizarUsuarioInput implements UsuarioInput {

    @NotBlank
    @Schema(example = "João das Neves")
    private String nome;

    @NotNull
    @Email
    @Schema(example = "joao_das_neves@email.com")
    private String email;

    @NotNull
    @CPF
    @Schema(example = "93012141057")
    private String cpf;

    @NotNull
    @Pattern(regexp = "\\d{11}")
    @Schema(example = "11987895687")
    private String telefone;

}
