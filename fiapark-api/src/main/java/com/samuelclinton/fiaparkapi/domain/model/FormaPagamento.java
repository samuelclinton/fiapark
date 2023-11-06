package com.samuelclinton.fiaparkapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.samuelclinton.fiaparkapi.domain.model.enums.TipoFormaPagamento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class FormaPagamento implements DomainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoFormaPagamento tipo;

    private String apelido;

    private String token;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Condutor condutor;

}
