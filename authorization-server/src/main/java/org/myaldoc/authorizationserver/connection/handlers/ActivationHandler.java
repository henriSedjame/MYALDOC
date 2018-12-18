package org.myaldoc.authorizationserver.connection.handlers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class ActivationHandler {
  @Value("${user.activation.success}")
  private String activationSuccess;
  @Value("${user.activation.failure}")
  private String activationFailure;

  public String getActivationSuccess(String name) {
    return MessageFormat.format(this.activationSuccess, name);
  }

  public String getActivationFailure(String id) {
    return MessageFormat.format(this.activationFailure, id);
  }

}
