package org.myaldoc.notificationserver.notifications.service;

import org.springframework.messaging.Message;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 29/11/2018
 * @Class purposes : .......
 */
public interface NotificationService<T> {

    default void notifyAccountCreation(Message<T> message) {
    }

    ;

    default void notifyAccountSuppression(Message<T> message) {
    }

    ;

}
