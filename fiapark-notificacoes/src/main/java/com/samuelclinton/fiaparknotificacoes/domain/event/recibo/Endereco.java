package com.samuelclinton.fiaparknotificacoes.domain.event.recibo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Endereco {

    private String rua;
    private String numero;
    private String cidade;
    private String estado;
    private String cep;

    public String getEnderecoFormatado() {
        return String.format("%s, %s, %s - %s, %s", rua, numero, cidade, estado, cep);
    }

}
