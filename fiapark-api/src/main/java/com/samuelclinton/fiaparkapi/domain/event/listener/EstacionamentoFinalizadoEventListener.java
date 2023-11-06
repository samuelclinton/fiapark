package com.samuelclinton.fiaparkapi.domain.event.listener;

import com.samuelclinton.fiaparkapi.domain.event.EstacionamentoFinalizadoEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static com.samuelclinton.fiaparkapi.core.rabbitmq.RabbitMQConfig.ESTACIONAMENTO_FINALIZADO_EXCHANGE;

@Component
public class EstacionamentoFinalizadoEventListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @EventListener
    public void aoFinalizarEstacionamento(EstacionamentoFinalizadoEvent event) {
        rabbitTemplate.convertAndSend(ESTACIONAMENTO_FINALIZADO_EXCHANGE, "", event);
    }

}
