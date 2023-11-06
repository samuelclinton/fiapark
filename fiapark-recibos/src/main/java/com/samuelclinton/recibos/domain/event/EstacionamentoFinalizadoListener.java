package com.samuelclinton.recibos.domain.event;

import com.samuelclinton.recibos.domain.model.Recibo;
import com.samuelclinton.recibos.domain.service.ReciboService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.samuelclinton.recibos.core.rabbitmq.RabbitMQConfig.QUEUE_GERAR_RECIBO;

@Slf4j
@Component
public class EstacionamentoFinalizadoListener {

    @Autowired
    private ReciboService reciboService;

    @Transactional
    @RabbitListener(queues = QUEUE_GERAR_RECIBO)
    public void aoFinalizarEstacionamento(EstacionamentoFinalizadoEvent event) {
        final var recibo = new Recibo(event);
        reciboService.salvar(recibo);
    }

}
