package com.samuelclinton.fiaparkapi.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Data
@Configuration
@ConfigurationProperties("fiapark.estacionamento")
public class EstacionamentoProperties {

    /**
     * Tarifa com o qual é calculado o valor total de um estacionamento. Por padrão 1.0.
     */
    private BigDecimal tarifa = BigDecimal.ONE;

}
