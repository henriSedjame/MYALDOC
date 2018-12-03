package org.myaldoc.notificationserver.notifications.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.myaldoc.core.messaging.Sms;
import org.myaldoc.notificationserver.configuration.messaging.SmsSink;
import org.myaldoc.notificationserver.notifications.service.NotificationService;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.stereotype.Service;

@Service
@EnableBinding(SmsSink.class)
@Slf4j
public class SmsNotificationServiceImpl implements NotificationService<Sms> {
}
