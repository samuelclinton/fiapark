package com.samuelclinton.fiaparkapi.domain.exception;

public abstract class ConflitoDeRecursoException extends RuntimeException {

    public ConflitoDeRecursoException(String message) {
        super(message);
    }

}
