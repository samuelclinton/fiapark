package com.samuelclinton.fiaparkapi.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
public class Condutor implements DomainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private Endereco endereco;

    @OneToMany(mappedBy = "condutor", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<FormaPagamento> formasPagamento = new HashSet<>();

    @OneToMany(mappedBy = "condutor", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Veiculo> veiculos = new HashSet<>();

    public void adicionarVeiculo(Veiculo veiculo) {
        veiculos.add(veiculo);
        veiculo.setCondutor(this);
    }

    public void removerVeiculo(Veiculo veiculo) {
        veiculos.remove(veiculo);
        veiculo.setCondutor(null);
    }

    public void adicionarFormaPagamento(FormaPagamento formaPagamento) {
        formasPagamento.add(formaPagamento);
        formaPagamento.setCondutor(this);
    }

    public void removerFormaPagamento(FormaPagamento formaPagamento) {
        formasPagamento.remove(formaPagamento);
        formaPagamento.setCondutor(null);
    }

}
