package com.samuelclinton.fiaparkapi.domain.data;

import com.samuelclinton.fiaparkapi.domain.model.DomainEntity;

import java.util.Collection;
import java.util.List;

public interface DomainEntityMapper<I extends InputModel, O extends OutputModel, E extends DomainEntity> {

    E mapearInputParaEntidade(I input, Class<E> entityClass);
    O mapearEntidadeParaOutput(E entity, Class<O> outputClass);
    List<O> mapearEntidadesParaOutputs(Collection<E> entities, Class<O> outputClass);
    void copiarConteudoEntreEntidades(E source, E destination);

}
