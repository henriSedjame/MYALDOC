package org.myaldoc.authorizationserver.connection.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface EmailSource {

  String ACCOUNT_CREATION_EMAIL_OUTPUT = "accountCreationEmailOutput";

  @Output(ACCOUNT_CREATION_EMAIL_OUTPUT)
  MessageChannel emailOutput();
}
