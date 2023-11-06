package com.samuelclinton.fiaparkapi.domain.exception;

public abstract class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String message) {
        super(message);
    }

}
