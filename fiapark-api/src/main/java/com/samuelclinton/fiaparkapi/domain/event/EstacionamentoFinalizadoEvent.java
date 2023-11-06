package com.samuelclinton.fiaparkapi.domain.event;

import com.samuelclinton.fiaparkapi.domain.data.OutputModel;
import com.samuelclinton.fiaparkapi.domain.model.Estacionamento;
import com.samuelclinton.fiaparkapi.domain.model.enums.TipoFormaPagamento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Duration;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class EstacionamentoFinalizadoEvent implements OutputModel {

    private CondutorEventModel condutor;
    private VeiculoEventModel veiculo;
    private TipoFormaPagamento tipoFormaPagamento;
    private Duration duracao;
    private BigDecimal tarifa;
    private BigDecimal valor;

    public EstacionamentoFinalizadoEvent(Estacionamento estacionamento) {

        final var condutor = estacionamento.getVeiculo().getCondutor();

        final var endereco = condutor.getEndereco();
        final var enderecoEventModel = EnderecoEventModel
                .builder()
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .cep(endereco.getCep())
                .build();

        this.condutor = CondutorEventModel
                .builder()
                .cpf(condutor.getCpf())
                .nome(condutor.getNome())
                .email(condutor.getEmail())
                .telefone(condutor.getTelefone())
                .endereco(enderecoEventModel)
                .build();

        final var veiculo = estacionamento.getVeiculo();
        this.veiculo = VeiculoEventModel
                .builder()
                .placa(veiculo.getPlaca())
                .cor(veiculo.getCor())
                .modelo(veiculo.getModelo())
                .marca(veiculo.getMarca())
                .build();

        this.tipoFormaPagamento = estacionamento.getFormaPagamento().getTipo();
        this.duracao = estacionamento.getDuracao();
        this.tarifa = estacionamento.getTarifa();
        this.valor = estacionamento.getValor();

    }

}
