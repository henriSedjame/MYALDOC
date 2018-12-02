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

    @Input("accountCreationEmailInput")
    SubscribableChannel emailInput();
}
