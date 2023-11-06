package com.samuelclinton.fiaparkapi.domain.repository;

import com.samuelclinton.fiaparkapi.domain.model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

    Optional<FormaPagamento> findByToken(String token);

}
