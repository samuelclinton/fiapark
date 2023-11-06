package com.samuelclinton.fiaparknotificacoes.domain.event.estacionamento;

import com.samuelclinton.fiaparknotificacoes.domain.service.NotificationService;
import com.samuelclinton.fiaparknotificacoes.domain.service.NotificationService.Notificacao;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.samuelclinton.fiaparknotificacoes.core.rabbitmq.RabbitMQConfig.QUEUE_NOTIFICAR_EXPIRACAO;

@Component
public class EstacionamentoExpirandoListener {

    @Autowired
    private NotificationService notificationService;

    @RabbitListener(queues = QUEUE_NOTIFICAR_EXPIRACAO)
    public void aoProximoDeExpirar(EstacionamentoExpirandoEvent event) {
        var notificacao = Notificacao.builder()
                .destinatario(event.getEmailCondutor())
                .assunto("Estacionamento acabando")
                .corpo("expiracao.html")
                .variavel("evento", event)
                .build();

        notificationService.enviar(notificacao);
    }

}
