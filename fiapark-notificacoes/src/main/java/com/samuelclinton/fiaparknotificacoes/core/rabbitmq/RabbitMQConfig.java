package com.samuelclinton.fiaparknotificacoes.core.rabbitmq;

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

    public static final String RECIBO_GERADO_EXCHANGE = "recibos.v1.recibo-gerado";
    public static final String RECIBO_GERADO_DLX = RECIBO_GERADO_EXCHANGE + ".dlx";

    public static final String ESTACIONAMENTO_EXPIRANDO_EXCHANGE = "estacionamentos.v1.estacionamento-expirando";
    public static final String ESTACIONAMENTO_EXPIRANDO_DLX = ESTACIONAMENTO_EXPIRANDO_EXCHANGE + ".dlx";

    public static final String QUEUE_ENVIAR_RECIBO = RECIBO_GERADO_EXCHANGE + ".enviar-recibo";
    public static final String QUEUE_ENVIAR_RECIBO_DLQ = RECIBO_GERADO_DLX + ".enviar-recibo.dlq";

    public static final String QUEUE_NOTIFICAR_EXPIRACAO = ESTACIONAMENTO_EXPIRANDO_EXCHANGE + ".notificar-expiracao";
    public static final String QUEUE_NOTIFICAR_EXPIRACAO_DLQ = ESTACIONAMENTO_EXPIRANDO_DLX + ".notificar-expiracao.dlq";

    @Bean
    public Queue queueEnviarRecibo() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", RECIBO_GERADO_DLX);
        return new Queue(QUEUE_ENVIAR_RECIBO, true, false, false, args);
    }

    @Bean
    public Binding bindingEnviarRecibo() {
        var queue = queueEnviarRecibo();
        var exchange = new FanoutExchange(RECIBO_GERADO_EXCHANGE);
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public Queue queueEnviarReciboDeadLetterQueue() {
        return new Queue(QUEUE_ENVIAR_RECIBO_DLQ);
    }

    @Bean
    public Binding bindingEnviarReciboDeadLetterQueue() {
        var queue = queueEnviarReciboDeadLetterQueue();
        var exchange = new FanoutExchange(RECIBO_GERADO_DLX);
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public Queue queueNotificarExpiracao() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", ESTACIONAMENTO_EXPIRANDO_DLX);
        return new Queue(QUEUE_NOTIFICAR_EXPIRACAO, true, false, false, args);
    }

    @Bean
    public Binding bindingNotificarExpiracao() {
        var queue = queueNotificarExpiracao();
        var exchange = new FanoutExchange(ESTACIONAMENTO_EXPIRANDO_EXCHANGE);
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public Queue queueNotificarExpiracaoDeadLetterQueue() {
        return new Queue(QUEUE_NOTIFICAR_EXPIRACAO_DLQ);
    }

    @Bean
    public Binding bindingNotificarExpiracaoDeadLetterQueue() {
        var queue = queueNotificarExpiracaoDeadLetterQueue();
        var exchange = new FanoutExchange(ESTACIONAMENTO_EXPIRANDO_DLX);
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
