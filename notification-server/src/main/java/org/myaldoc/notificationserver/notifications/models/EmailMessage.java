package org.myaldoc.notificationserver.notifications.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.myaldoc.core.messaging.MyaldocEmailMessage;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 29/11/2018
 * @Class purposes : .......
 */

@Document(collection = "emails")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmailMessage extends MyaldocEmailMessage {
    @Id
    private String id;

    public EmailMessage clone(MyaldocEmailMessage source) {
        this.setSentToEmail(source.getSentToEmail());
        this.setSentToName(source.getSentToName());
        this.setSentSuccessful(source.isSentSuccessful());
        this.setSentDate(source.getSentDate());
        return this;
    }
}
