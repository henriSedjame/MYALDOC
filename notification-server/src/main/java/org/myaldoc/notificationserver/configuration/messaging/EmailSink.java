package org.myaldoc.notificationserver.configuration.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 29/11/2018
 * @Class purposes : .......
 */
public interface EmailSink {

    String ACCOUNT_CREATION_EMAIL_INPUT = "accountCreationEmailInput";
    String ACCOUNT_DELETION_EMAIL_INPUT = "accountDeletionEmailInput";
    String ACCOUNT_ACTIVATION_EMAIL_INPUT = "accountActivationEmailInput";

    @Input(ACCOUNT_CREATION_EMAIL_INPUT)
    SubscribableChannel accountCreationEmailInput();

    @Input(ACCOUNT_DELETION_EMAIL_INPUT)
    SubscribableChannel accountDeletionEmailInput();

    @Input(ACCOUNT_ACTIVATION_EMAIL_INPUT)
    SubscribableChannel accountActivationEmailInput();

}
