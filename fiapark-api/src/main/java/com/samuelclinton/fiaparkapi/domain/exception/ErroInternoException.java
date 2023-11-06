package com.samuelclinton.fiaparkapi.domain.exception;

public abstract class ErroInternoException extends RuntimeException {

    public ErroInternoException(String message) {
        super(message);
    }

}
