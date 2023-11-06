package com.samuelclinton.fiaparknotificacoes.domain.service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

import java.util.Map;

public interface NotificationService {

    void enviar(Notificacao notificacao);

    @Getter
    @Builder
    class Notificacao {

        @NonNull
        private String destinatario;

        @NonNull
        private String assunto;

        @NonNull
        private String corpo;

        @Singular("variavel")
        private Map<String, Object> variaveis;

    }

}
