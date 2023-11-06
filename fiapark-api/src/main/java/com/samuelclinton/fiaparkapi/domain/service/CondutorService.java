package com.samuelclinton.fiaparkapi.domain.service;

import com.samuelclinton.fiaparkapi.api.model.output.CondutorResumoOutput;
import com.samuelclinton.fiaparkapi.api.model.input.UsuarioInput;
import com.samuelclinton.fiaparkapi.domain.exception.CondutorExistenteException;
import com.samuelclinton.fiaparkapi.domain.exception.CondutorNaoEncontradoException;
import com.samuelclinton.fiaparkapi.domain.model.Condutor;
import com.samuelclinton.fiaparkapi.domain.model.Endereco;
import com.samuelclinton.fiaparkapi.domain.repository.CondutorRepository;
import com.samuelclinton.fiaparkapi.infrastructure.data.DomainEntityMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CondutorService {

    @Autowired
    private CondutorRepository condutorRepository;

    @Autowired
    private DomainEntityMapperImpl<UsuarioInput, CondutorResumoOutput, Condutor> condutorMapper;

    public Condutor buscar(Long condutorId) {
        return condutorRepository.findById(condutorId)
                .orElseThrow(() ->
                        new CondutorNaoEncontradoException("Nenhum condutor encontrado com o id " + condutorId));
    }

    @Transactional
    public Condutor cadastrar(Condutor novoCondutor) {
        verificarDuplicidade(novoCondutor);
        return salvar(novoCondutor);
    }

    @Transactional
    public Condutor atualizar(Condutor condutorExistente, Condutor condutorAtualizado) {
        verificarDuplicidade(condutorAtualizado);
        condutorMapper.copiarConteudoEntreEntidades(condutorAtualizado, condutorExistente);
        return salvar(condutorExistente);
    }

    @Transactional
    public Condutor salvar(Condutor condutor) {
        return condutorRepository.save(condutor);
    }

    private void verificarDuplicidade(Condutor condutor) {
        final var mensagem = "Já existe um condutor cadastrado com o %s %s";
        condutorRepository
                .findByCpfOrEmailOrTelefone(condutor.getCpf(), condutor.getEmail(), condutor.getTelefone())
            .ifPresent(condutorExistente -> {
                if (condutorExistente.getCpf().equals(condutor.getCpf())) {
                    throw new CondutorExistenteException(
                            String.format(mensagem, "CPF", condutorExistente.getCpf()));
                } else if (condutorExistente.getEmail().equals(condutor.getEmail())) {
                    throw new CondutorExistenteException(
                            String.format(mensagem, "email", condutorExistente.getEmail()));
                } else {
                    throw new CondutorExistenteException(
                            String.format(mensagem, "telefone", condutorExistente.getTelefone()));
                }
            });
    }

    @Transactional
    public Condutor atualizarEndereco(Condutor condutor, Endereco novoEndereco) {
        condutor.setEndereco(novoEndereco);
        return condutorRepository.save(condutor);
    }

    @Transactional
    public void excluir(Condutor condutor) {
        condutorRepository.delete(condutor);
    }

}
