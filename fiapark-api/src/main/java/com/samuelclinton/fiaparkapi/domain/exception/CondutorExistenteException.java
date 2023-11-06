package com.samuelclinton.fiaparkapi.domain.exception;

public class CondutorExistenteException extends ConflitoDeRecursoException {

    public CondutorExistenteException(String message) {
        super(message);
    }

}
