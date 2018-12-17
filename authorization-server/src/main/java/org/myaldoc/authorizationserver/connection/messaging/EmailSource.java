package org.myaldoc.authorizationserver.connection.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface EmailSource {

  String ACCOUNT_CREATION_EMAIL_OUTPUT = "accountCreationEmailOutput";
  String ACCOUNT_DELETION_EMAIL_OUTPUT = "accountDeletionEmailOutput";
  String ACCOUNT_ACTIVATION_EMAIL_OUTPUT = "accountActivationEmailOutput";

  @Output(ACCOUNT_CREATION_EMAIL_OUTPUT)
  MessageChannel accountCreationEmailOutput();

  @Output(ACCOUNT_DELETION_EMAIL_OUTPUT)
  MessageChannel accountDeletionEmailOutput();

  @Output(ACCOUNT_ACTIVATION_EMAIL_OUTPUT)
  MessageChannel accountActivationEmailOutput();
}
