package org.myaldoc.notificationserver.notifications.service.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.myaldoc.core.messaging.Mail;
import org.myaldoc.notificationserver.configuration.messaging.EmailSink;
import org.myaldoc.notificationserver.notifications.models.EmailMessage;
import org.myaldoc.notificationserver.notifications.repositories.EmailMessageRepository;
import org.myaldoc.notificationserver.notifications.service.NotificationService;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 29/11/2018
 * @Class purposes : .......
 */

@Service
@Slf4j
@EnableBinding(EmailSink.class)
public class EmailNotificationServiceImpl implements NotificationService<Mail> {

    private EmailMessageRepository emailMessageRepository;
    private JavaMailSender mailSender;
    private Configuration configuration;

    public EmailNotificationServiceImpl(EmailMessageRepository emailMessageRepository,
                                        JavaMailSender mailSender,
                                        Configuration configuration) {
        this.emailMessageRepository = emailMessageRepository;
        this.mailSender = mailSender;
        this.configuration = configuration;
    }

    @StreamListener(EmailSink.ACCOUNT_CREATION_EMAIL_INPUT)
    @Override
    public void notifyAccountCreation(Message<Mail> message) {

        log.info("Compte creation notification starts.");

        Map<String, String> variables = new HashMap<>();
        variables.put("Title", "Confirmation de création de compte");
        variables.put("Name", "Henri SEDJAME");
        variables.put("Location", "Niort");

        this.sendMessage(message, variables);

        log.info("Compte creation notification ends.");
    }

    @StreamListener(EmailSink.ACCOUNT_DELETIONN_EMAIL_INPUT)
    @Override
    public void notifyAccountSuppression(Message<Mail> message) {

        log.info("Compte creation notification starts.");

        Map<String, String> variables = new HashMap<>();
        variables.put("Title", "Confirmation de suppression de compte");
        variables.put("Name", "Henri SEDJAME");
        variables.put("Location", "Niort");

        this.sendMessage(message, variables);

        log.info("Compte creation notification ends.");
    }

    private void sendMessage(Message<Mail> message, Map<String, String> variables) {
        final Mail emailMessage = message.getPayload();

        final MimeMessage mimeMessage = mailSender.createMimeMessage();

        boolean sendSuccessfully = true;

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            //mimeMessageHelper.addAttachment("", new ClassPathResource(""));

            final Template template = configuration.getTemplate("email-template.ftl");

            final String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, variables);

            this.buildAndSendEmail(emailMessage, mimeMessage, mimeMessageHelper, html);

        } catch (IOException | TemplateException | MessagingException e) {
            sendSuccessfully = false;
            log.error(e.getMessage());
        }

        emailMessage.setSentDate(LocalDate.now());
        emailMessage.setSentSuccessfully(sendSuccessfully);

        final EmailMessage mail = new EmailMessage();
        this.emailMessageRepository.insert(mail.clone(emailMessage))
                .subscribe(em -> log.info("email envoyé à :" + em.getSentToName() + " (" + em.getSentToEmail() + ")"));
    }

    private void buildAndSendEmail(Mail emailMessage, MimeMessage mimeMessage, MimeMessageHelper mimeMessageHelper, String html) throws MessagingException {
        mimeMessageHelper.setTo(emailMessage.getSentToEmail());
        mimeMessageHelper.setSubject("Confirmation de création de compte");
        mimeMessageHelper.setText(html, true);
        mimeMessageHelper.setFrom("sedhjo@gmail.com");
        mailSender.send(mimeMessage);
    }

}
