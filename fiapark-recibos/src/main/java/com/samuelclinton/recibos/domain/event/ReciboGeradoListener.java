package com.samuelclinton.recibos.domain.event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static com.samuelclinton.recibos.core.rabbitmq.RabbitMQConfig.RECIBO_GERADO_EXCHANGE;

@Component
public class ReciboGeradoListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @EventListener
    public void aoGerarRecibo(ReciboGeradoEvent event) {
        rabbitTemplate.convertAndSend(RECIBO_GERADO_EXCHANGE, "", event);
    }

}
