package org.myaldoc.authorizationserver.connection.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 02/12/2018
 * @Class purposes : .......
 */
public interface EmailSource {

    String ACCOUNT_CREATION_EMAIL_OUPUT = "accountCreationEmailOutput";

    @Output("accountCreationEmailOutput")
    MessageChannel emailOutput();
}
