package org.myaldoc.notificationserver.configuration.twilio;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("twilio")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TwilioConfig {
  private String accountSid;
  private String authToken;
  private String number;
}
