package com.samuelclinton.recibos.api.controller;

import com.samuelclinton.recibos.api.controller.openapi.ReciboControllerOpenApi;
import com.samuelclinton.recibos.api.model.ReciboOutput;
import com.samuelclinton.recibos.domain.model.Recibo;
import com.samuelclinton.recibos.domain.repository.ReciboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recibos")
public class ReciboController implements ReciboControllerOpenApi {

    @Autowired
    private ReciboRepository reciboRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReciboOutput> listar(@RequestParam(value = "cpf", required = false) String cpf) {

        List<Recibo> recibos = cpf == null ?
                reciboRepository.findAll() : reciboRepository.findByCondutorCpf(cpf);

        return recibos.stream().map(ReciboOutput::new).toList();

    }

}
