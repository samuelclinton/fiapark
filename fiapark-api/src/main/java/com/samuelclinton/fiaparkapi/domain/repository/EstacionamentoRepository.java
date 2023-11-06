package com.samuelclinton.fiaparkapi.domain.repository;

import com.samuelclinton.fiaparkapi.domain.model.Estacionamento;
import com.samuelclinton.fiaparkapi.domain.model.Veiculo;
import com.samuelclinton.fiaparkapi.domain.model.enums.StatusEstacionamento;
import com.samuelclinton.fiaparkapi.domain.model.enums.TipoEstacionamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface EstacionamentoRepository extends JpaRepository<Estacionamento, Long> {

    Optional<Estacionamento> findByVeiculoAndStatusIs(Veiculo veiculo, StatusEstacionamento status);

    List<Estacionamento> findByExpiracaoNotificadaIsFalseAndDataTerminoBefore(Instant data);

    List<Estacionamento> findByTipoAndStatusAndDataTerminoBefore(TipoEstacionamento tipo,
                                                                 StatusEstacionamento status,
                                                                 Instant data);

}
