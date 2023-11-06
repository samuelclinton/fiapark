package com.samuelclinton.fiaparkapi.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    RECURSO_NAO_ENCONTRADO("Recurso não encontrado", "recurso-nao-encontrado"),
    CONFLITO_DE_RECURSOS("Conflito de recursos", "conflito-de-recursos"),
    MENSAGEM_INCOMPREENSIVEL("Mensagem incrompreensível", "mensagem-incompreensivel"),
    PARAMETRO_INVALIDO("Parâmetro inválido", "parametro-invalido"),
    ERRO_DE_SISTEMA("Erro de sistema", "erro-de-sistema"),
    DADOS_INVALIDOS("Dados inválidos", "dados-invalidos"),
    ERRO_NEGOCIO("Violação de regra de negócio", "erro-negocio");

    private final String title;
    private final String uri;

    ProblemType(String title, String path) {
        this.title = title;
        this.uri = "https://parquimetros.com.br/" + path;
    }

}
