package com.samuelclinton.fiaparknotificacoes.domain.event.recibo;

import com.samuelclinton.fiaparknotificacoes.domain.service.NotificationService;
import com.samuelclinton.fiaparknotificacoes.domain.service.NotificationService.Notificacao;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static com.samuelclinton.fiaparknotificacoes.core.rabbitmq.RabbitMQConfig.QUEUE_ENVIAR_RECIBO;

@Component
public class ReciboGeradoEventListener {

    @Autowired
    private NotificationService notificationService;

    @RabbitListener(queues = QUEUE_ENVIAR_RECIBO)
    public void aoProximoDeExpirar(ReciboGeradoEvent event) {

        var variaveis = new HashMap<String, Object>();

        variaveis.put("evento", event);
        variaveis.put("dadosCondutor", event.getCondutor().getDadosCondutorFormatados());
        variaveis.put("enderecoFormatado", event.getCondutor().getEndereco().getEnderecoFormatado());

        var notificacao = Notificacao.builder()
                .destinatario(event.getCondutor().getEmail())
                .assunto("Seu recibo de estacionamento")
                .corpo("recibo.html")
                .variaveis(variaveis)
                .build();

        notificationService.enviar(notificacao);
    }

}
