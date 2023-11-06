package com.samuelclinton.recibos.core.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    public static final String ESTACIONAMENTO_FINALIZADO_EXCHANGE = "estacionamentos.v1.estacionamento-finalizado";
    public static final String ESTACIONAMENTO_FINALIZADO_DLX = ESTACIONAMENTO_FINALIZADO_EXCHANGE + ".dlx";
    public static final String RECIBO_GERADO_EXCHANGE = "recibos.v1.recibo-gerado";
    public static final String RECIBO_GERADO_DLX = RECIBO_GERADO_EXCHANGE + ".dlx";
    public static final String QUEUE_GERAR_RECIBO = ESTACIONAMENTO_FINALIZADO_EXCHANGE + ".gerar-recibo";
    public static final String QUEUE_GERAR_RECIBO_DLQ = ESTACIONAMENTO_FINALIZADO_DLX + ".gerar-recibo.dlq";

    @Bean
    public FanoutExchange reciboGeradoExchange() {
        return new FanoutExchange(RECIBO_GERADO_EXCHANGE);
    }

    @Bean
    public FanoutExchange reciboGeradoDLX() {
        return new FanoutExchange(RECIBO_GERADO_DLX);
    }

    @Bean
    public Queue queueGerarRecibo() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", ESTACIONAMENTO_FINALIZADO_DLX);
        return new Queue(QUEUE_GERAR_RECIBO, true, false, false, args);
    }

    @Bean
    public Binding bindingGerarRecibo() {
        var queue = queueGerarRecibo();
        var exchange = new FanoutExchange(ESTACIONAMENTO_FINALIZADO_EXCHANGE);
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public Queue queueGerarReciboDeadLetterQueue() {
        return new Queue(QUEUE_GERAR_RECIBO_DLQ);
    }

    @Bean
    public Binding bindingGerarReciboDeadLetterQueue() {
        var queue = queueGerarReciboDeadLetterQueue();
        var exchange = new FanoutExchange(ESTACIONAMENTO_FINALIZADO_DLX);
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
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

    @Bean
    public ApplicationListener<ApplicationReadyEvent> applicationReadyEventApplicationListener(
            RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

}
