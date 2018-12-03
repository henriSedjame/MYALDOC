package org.myaldoc.core.messaging;

import lombok.*;

import javax.validation.constraints.Email;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Mail {

  @Email
  private String sentToEmail;
  private String sentToName;
  private LocalDate sentDate;
  private boolean sentSuccessfully;
}
