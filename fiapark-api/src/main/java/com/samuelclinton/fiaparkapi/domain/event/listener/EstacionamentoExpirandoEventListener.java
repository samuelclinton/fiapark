package com.samuelclinton.fiaparkapi.domain.event.listener;

import com.samuelclinton.fiaparkapi.domain.event.EstacionamentoExpirandoEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static com.samuelclinton.fiaparkapi.core.rabbitmq.RabbitMQConfig.ESTACIONAMENTO_EXPIRANDO_EXCHANGE;

@Component
public class EstacionamentoExpirandoEventListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @EventListener
    public void aoFinalizarEstacionamento(EstacionamentoExpirandoEvent event) {
        rabbitTemplate.convertAndSend(ESTACIONAMENTO_EXPIRANDO_EXCHANGE, "", event);
    }

}
