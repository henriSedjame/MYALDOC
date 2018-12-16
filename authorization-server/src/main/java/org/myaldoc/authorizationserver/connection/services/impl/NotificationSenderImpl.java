package org.myaldoc.authorizationserver.connection.services.impl;

import org.myaldoc.authorizationserver.connection.messaging.EmailSource;
import org.myaldoc.authorizationserver.connection.models.User;
import org.myaldoc.authorizationserver.connection.services.NotificationSender;
import org.myaldoc.core.messaging.Mail;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 09/12/2018
 * @Class purposes : Envoyer des notifications
 */
@Component
public class NotificationSenderImpl implements NotificationSender {

    private EmailSource emailSource;

    public NotificationSenderImpl(EmailSource emailSource) {
        this.emailSource = emailSource;
    }

    /**
     * ENVOI DE NOTIFICATION DE CREATION D'UN COMPTE
     *
     * @param user
     */
    public void notifyAccountCreation(User user) {
        emailSource.accountCreationEmailOutput()
                .send(MessageBuilder
                        .withPayload(Mail
                                .builder()
                                .sentToName(user.getUsername())
                                .sentToEmail(user.getEmail())
                                .build())
                        .build());
    }

    /**
     * ENVOI DE NOTIFICATION DE SUPPRESSION DE COMPTE
     *
     * @param user
     */
    @Override
    public void notifyAccountDeletion(User user) {
        emailSource.accountDeletionEmailOutput()
                .send(MessageBuilder
                        .withPayload(Mail
                                .builder()
                                .sentToName(user.getUsername())
                                .sentToEmail(user.getEmail())
                                .build())
                        .build());
    }
}
