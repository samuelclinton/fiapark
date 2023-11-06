package com.samuelclinton.recibos.domain.repository;

import com.samuelclinton.recibos.domain.model.Recibo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReciboRepository extends JpaRepository<Recibo, Long> {

    List<Recibo> findByCondutorCpf(String cpf);

}
