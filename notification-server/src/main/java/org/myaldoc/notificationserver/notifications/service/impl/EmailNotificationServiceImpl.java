package org.myaldoc.notificationserver.notifications.service.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.myaldoc.core.messaging.MyaldocEmailMessage;
import org.myaldoc.notificationserver.configuration.freemarker.templates.Templates;
import org.myaldoc.notificationserver.configuration.messaging.EmailSink;
import org.myaldoc.notificationserver.notifications.models.EmailMessage;
import org.myaldoc.notificationserver.notifications.repositories.EmailMessageRepository;
import org.myaldoc.notificationserver.notifications.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 29/11/2018
 * @Class purposes : .......
 */

@Service
@Slf4j
@EnableBinding(EmailSink.class)
public class EmailNotificationServiceImpl implements NotificationService<MyaldocEmailMessage> {

    private EmailMessageRepository emailMessageRepository;
    private JavaMailSender mailSender;
    private Configuration configuration;
    @Autowired
    @Qualifier("accountCreationemailTemplate")
    private Templates accountCreationEmailTemplate;

    public EmailNotificationServiceImpl(EmailMessageRepository emailMessageRepository, JavaMailSender mailSender, Configuration configuration) {
        this.emailMessageRepository = emailMessageRepository;
        this.mailSender = mailSender;
        this.configuration = configuration;
    }

    @StreamListener(EmailSink.ACCOUNT_CREATION_EMAIL_INPUT)
    @Override
    public void notifyAccountCreation(Message<MyaldocEmailMessage> message) {
        log.info("Compte creation notification starts.");

        final MyaldocEmailMessage emailMessage = message.getPayload();

        final MimeMessage mimeMessage = mailSender.createMimeMessage();

        boolean sendSuccessfully = true;

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            //mimeMessageHelper.addAttachment("", new ClassPathResource(""));

            final Template template = configuration.getTemplate("email-template.ftl");

            final String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, accountCreationEmailTemplate.getVariables());

            mimeMessageHelper.setTo(emailMessage.getSentToEmail());
            mimeMessageHelper.setSubject("Confirmation de création de compte");
            mimeMessageHelper.setText(html, true);
            mimeMessageHelper.setFrom("sedhjo@gmail.com");

            mailSender.send(mimeMessage);

        } catch (IOException | TemplateException | javax.mail.MessagingException e) {
            sendSuccessfully = false;
            log.error(e.getMessage());
        }

        emailMessage.setSentDate(LocalDate.now());
        emailMessage.setSentSuccessful(sendSuccessfully);

        final EmailMessage mail = new EmailMessage();
        this.emailMessageRepository.insert(mail.clone(emailMessage))
                .subscribe(em -> log.info("email envoyé à :" + em.getSentToName() + " (" + em.getSentToEmail() + ")"));

        log.info("Compte creation notification ends.");
    }

}
