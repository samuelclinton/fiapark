package com.samuelclinton.fiaparknotificacoes.domain.event.recibo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Condutor {

    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private Endereco endereco;

    public String getDadosCondutorFormatados() {
        return String.format("%s | %s | %s", nome, cpf, formatarTelefone());
    }

    private String formatarTelefone() {
        var ddd = this.telefone.substring(0, 2);
        var telefone = this.telefone.substring(2);
        return String.format("(%s) %s", ddd, telefone);
    }

}
