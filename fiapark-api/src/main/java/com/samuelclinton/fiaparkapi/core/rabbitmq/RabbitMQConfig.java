package com.samuelclinton.fiaparkapi.core.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String ESTACIONAMENTO_FINALIZADO_EXCHANGE = "estacionamentos.v1.estacionamento-finalizado";
    public static final String ESTACIONAMENTO_FINALIZADO_DLX = ESTACIONAMENTO_FINALIZADO_EXCHANGE + ".dlx";
    public static final String ESTACIONAMENTO_EXPIRANDO_EXCHANGE = "estacionamentos.v1.estacionamento-expirando";
    public static final String ESTACIONAMENTO_EXPIRANDO_DLX = ESTACIONAMENTO_EXPIRANDO_EXCHANGE + ".dlx";

    @Bean
    public FanoutExchange estacionamentoFinalizadoFanoutExchange() {
        return new FanoutExchange(ESTACIONAMENTO_FINALIZADO_EXCHANGE);
    }

    @Bean
    public FanoutExchange estacionamentoExpirandoFanoutExchange() {
        return new FanoutExchange(ESTACIONAMENTO_EXPIRANDO_EXCHANGE);
    }

    @Bean
    public FanoutExchange estacionamentoFinalizadoDLX() {
        return new FanoutExchange(ESTACIONAMENTO_FINALIZADO_DLX);
    }

    @Bean
    public FanoutExchange estacionamentoExpirandoDLX() {
        return new FanoutExchange(ESTACIONAMENTO_EXPIRANDO_DLX);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> applicationReadyEventApplicationListener(
            RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

}
