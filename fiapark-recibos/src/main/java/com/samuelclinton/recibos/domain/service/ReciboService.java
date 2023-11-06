package com.samuelclinton.recibos.domain.service;

import com.samuelclinton.recibos.domain.model.Recibo;
import com.samuelclinton.recibos.domain.repository.ReciboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReciboService {

    @Autowired
    private ReciboRepository reciboRepository;

    @Transactional
    public void salvar(Recibo recibo) {
        reciboRepository.saveAndFlush(recibo);
    }

}
