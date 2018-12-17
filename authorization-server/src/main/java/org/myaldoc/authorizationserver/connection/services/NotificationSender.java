package org.myaldoc.authorizationserver.connection.services;

import org.myaldoc.authorizationserver.connection.models.User;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 09/12/2018
 * @Class purposes : .......
 */
public interface NotificationSender {

    void notifyAccountCreation(User user);

    void notifyAccountDeletion(User user);

    void notifyAccountActivation(User user);
}
