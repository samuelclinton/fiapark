package com.samuelclinton.fiaparkapi.infrastructure.data;

import com.samuelclinton.fiaparkapi.domain.data.DomainEntityMapper;
import com.samuelclinton.fiaparkapi.domain.data.InputModel;
import com.samuelclinton.fiaparkapi.domain.data.OutputModel;
import com.samuelclinton.fiaparkapi.domain.model.DomainEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class DomainEntityMapperImpl<I extends InputModel, O extends OutputModel, E extends DomainEntity>
        implements DomainEntityMapper<I, O, E> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public E mapearInputParaEntidade(I input, Class<E> entityClass) {
        return this.modelMapper.map(input, entityClass);
    }

    @Override
    public O mapearEntidadeParaOutput(E entity, Class<O> outputClass) {
        return this.modelMapper.map(entity, outputClass);
    }

    @Override
    public List<O> mapearEntidadesParaOutputs(Collection<E> entities, Class<O> outputClass) {
        return entities.stream()
                .map(entidade -> this.mapearEntidadeParaOutput(entidade, outputClass))
                .toList();
    }

    @Override
    public void copiarConteudoEntreEntidades(E source, E destination) {
        this.modelMapper.map(source, destination);
    }

}
