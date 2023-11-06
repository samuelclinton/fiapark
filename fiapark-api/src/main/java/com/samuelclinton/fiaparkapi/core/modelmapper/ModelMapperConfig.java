package com.samuelclinton.fiaparkapi.core.modelmapper;

import com.samuelclinton.fiaparkapi.api.model.input.NovaFormaPagamentoInput;
import com.samuelclinton.fiaparkapi.domain.model.FormaPagamento;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        Converter<String, String> toToken =
                context -> context.getSource() == null ? null : tokenizarCartao(context.getSource());

        TypeMap<NovaFormaPagamentoInput, FormaPagamento> formaPagamentoTypeMap =
                modelMapper.createTypeMap(NovaFormaPagamentoInput.class, FormaPagamento.class);

        formaPagamentoTypeMap.addMappings(mapper -> mapper.using(toToken).map(NovaFormaPagamentoInput::getCartao, FormaPagamento::setToken));

        return modelMapper;
    }

    private String tokenizarCartao(String cartao) {
        return Base64.getEncoder().encodeToString(cartao.getBytes(StandardCharsets.UTF_8));
    }

}
