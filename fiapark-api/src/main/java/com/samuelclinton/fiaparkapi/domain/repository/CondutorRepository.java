package com.samuelclinton.fiaparkapi.domain.repository;

import com.samuelclinton.fiaparkapi.domain.model.Condutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CondutorRepository extends JpaRepository<Condutor, Long> {

    Optional<Condutor> findByCpfOrEmailOrTelefone(String cpf, String email, String telefone);

}
