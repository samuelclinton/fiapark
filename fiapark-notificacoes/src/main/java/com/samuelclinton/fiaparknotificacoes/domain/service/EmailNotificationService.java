package com.samuelclinton.fiaparknotificacoes.domain.service;

import com.samuelclinton.fiaparknotificacoes.domain.exception.NotificacaoException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Service
public class EmailNotificationService implements NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Configuration freemarkerConfig;

    @Override
    public void enviar(Notificacao notificacao) {
        try {
            MimeMessage mimeMessage = criarMimeMessage(notificacao);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new NotificacaoException("Não foi possível enviar o e-mail", e);
        }
    }

    private MimeMessage criarMimeMessage(Notificacao notificacao) throws Exception {
        String corpo = processarTemplate(notificacao);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setFrom("Fiapark");
        helper.setTo(notificacao.getDestinatario());
        helper.setSubject(notificacao.getAssunto());
        helper.setText(corpo, true);
        return mimeMessage;
    }

    protected String processarTemplate(Notificacao notificacao) throws Exception {
        Template template = freemarkerConfig.getTemplate(notificacao.getCorpo());
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, notificacao.getVariaveis());
    }

}
