package org.myaldoc.core.messaging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sms {
  private String twilioSid;
  private String sentToNumber;
  private String sentToName;
  private LocalDate sentDate;
  private boolean sentSuccessfully;
}
