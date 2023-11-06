package com.samuelclinton.fiaparkapi.domain.exception;

public class EstacionamentoExistenteException extends ConflitoDeRecursoException {

    public EstacionamentoExistenteException(String message) {
        super(message);
    }

}
