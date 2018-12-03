package org.myaldoc.notificationserver.notifications.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.myaldoc.core.messaging.Sms;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SmsMessage extends Sms {

  @Id
  private String id;

  public SmsMessage clone(Sms source) {
    this.setSentToName(source.getSentToName());
    this.setSentToNumber(source.getSentToNumber());
    this.setSentDate(source.getSentDate());
    this.setSentSuccessfully(source.isSentSuccessfully());
    return this;
  }
}
