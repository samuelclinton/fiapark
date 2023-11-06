package com.samuelclinton.fiaparkapi.domain.exception;

public class FormaPagamentoExistenteException extends ConflitoDeRecursoException {

    public FormaPagamentoExistenteException(String message) {
        super(message);
    }

}
