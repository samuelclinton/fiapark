package com.samuelclinton.fiaparkapi.domain.service;

import com.samuelclinton.fiaparkapi.domain.exception.FormaPagamentoExistenteException;
import com.samuelclinton.fiaparkapi.domain.exception.FormaPagamentoNaoEncontradaException;
import com.samuelclinton.fiaparkapi.domain.model.FormaPagamento;
import com.samuelclinton.fiaparkapi.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FormaPagamentoService {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    public FormaPagamento buscar(Long formaPagamentoId) {
        return formaPagamentoRepository.findById(formaPagamentoId)
                .orElseThrow(() ->
                        new FormaPagamentoNaoEncontradaException("Nenhuma forma de pagamento encontrada com o id "
                                + formaPagamentoId));
    }

    @Transactional
    public FormaPagamento cadastrar(FormaPagamento formaPagamento) {
        verificarDuplicidade(formaPagamento);
        formaPagamento.getCondutor().adicionarFormaPagamento(formaPagamento);
        return formaPagamentoRepository.saveAndFlush(formaPagamento);
    }

    @Transactional
    public void excluir(FormaPagamento formaPagamento) {
        formaPagamentoRepository.delete(formaPagamento);
    }

    private void verificarDuplicidade(FormaPagamento formaPagamento) {
        final var mensagem = "Já existe uma forma de pagamento cadastrada com esse número de cartão";
        formaPagamentoRepository
                .findByToken(formaPagamento.getToken())
                .ifPresent(formaPagamentoExistente -> {
                    throw new FormaPagamentoExistenteException(mensagem);
                });
    }

}
