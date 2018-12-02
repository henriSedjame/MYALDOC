package org.myaldoc.core.messaging;

import lombok.*;

import javax.validation.constraints.Email;
import java.time.LocalDate;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 29/11/2018
 * @Class purposes : .......
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MyaldocEmailMessage {
    @Email
    private String sentToEmail;
    private String sentToName;
    private boolean sentSuccessful;
    private LocalDate sentDate;
}
