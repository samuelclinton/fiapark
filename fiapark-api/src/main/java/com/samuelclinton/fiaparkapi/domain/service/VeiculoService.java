package com.samuelclinton.fiaparkapi.domain.service;

import com.samuelclinton.fiaparkapi.api.model.input.VeiculoInput;
import com.samuelclinton.fiaparkapi.api.model.output.VeiculoOutput;
import com.samuelclinton.fiaparkapi.domain.exception.CondutorExistenteException;
import com.samuelclinton.fiaparkapi.domain.exception.CondutorNaoEncontradoException;
import com.samuelclinton.fiaparkapi.domain.model.Veiculo;
import com.samuelclinton.fiaparkapi.domain.repository.VeiculoRepository;
import com.samuelclinton.fiaparkapi.infrastructure.data.DomainEntityMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private CondutorService condutorService;

    @Autowired
    private DomainEntityMapperImpl<VeiculoInput, VeiculoOutput, Veiculo> veiculoMapper;

    public Veiculo buscar(Long veiculoId) {
        return veiculoRepository.findById(veiculoId)
                .orElseThrow(() ->
                        new CondutorNaoEncontradoException("Nenhum veículo encontrado com o id " + veiculoId));
    }

    @Transactional
    public Veiculo cadastrar(Veiculo veiculo) {
        verificarDuplicidade(veiculo);
        final var condutor = condutorService.buscar(veiculo.getCondutor().getId());
        veiculo.setPlaca(veiculo.getPlaca().toUpperCase());
        condutor.adicionarVeiculo(veiculo);
        return veiculoRepository.saveAndFlush(veiculo);
    }

    @Transactional
    public Veiculo atualizar(Veiculo veiculoExistente, Veiculo veiculoAtualizado) {
        veiculoAtualizado.setPlaca(veiculoAtualizado.getPlaca().toUpperCase());
        veiculoMapper.copiarConteudoEntreEntidades(veiculoAtualizado, veiculoExistente);
        return veiculoRepository.saveAndFlush(veiculoExistente);
    }

    @Transactional
    public void excluir(Veiculo veiculo) {
        veiculoRepository.delete(veiculo);
    }

    private void verificarDuplicidade(Veiculo veiculo) {
        final var mensagem = "Já existe um veiculo cadastrado com a placa %s";
        veiculoRepository
                .findByPlaca(veiculo.getPlaca())
                .ifPresent(veiculoExistente -> {
                    throw new CondutorExistenteException(
                            String.format(mensagem, veiculoExistente.getPlaca()));
                });
    }

}
