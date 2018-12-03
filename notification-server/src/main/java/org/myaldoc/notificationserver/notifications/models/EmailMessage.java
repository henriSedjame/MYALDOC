package org.myaldoc.notificationserver.notifications.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.myaldoc.core.messaging.Mail;
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
public class EmailMessage extends Mail {
    @Id
    private String id;

    public EmailMessage clone(Mail source) {
        this.setSentToEmail(source.getSentToEmail());
        this.setSentToName(source.getSentToName());
        this.setSentSuccessfully(source.isSentSuccessfully());
        this.setSentDate(source.getSentDate());
        return this;
    }
}
