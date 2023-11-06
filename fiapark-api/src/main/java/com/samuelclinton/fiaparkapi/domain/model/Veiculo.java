package com.samuelclinton.fiaparkapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Veiculo implements DomainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String placa;
    private String cor;
    private String marca;
    private String modelo;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Condutor condutor;

}
