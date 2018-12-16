package org.myaldoc.notificationserver.notifications.aspects;

import com.twilio.Twilio;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.myaldoc.notificationserver.configuration.twilio.TwilioConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TwilioAspect {

  @Autowired
  private TwilioConfig twilioConfig;

  private void init() {
    Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
  }

  @Before("twilioPointCut()")
  public void twilioInit() {
    this.init();
  }

  @Pointcut("execution(public * org.myaldoc.notificationserver.notifications.service.impl.SmsNotificationServiceImpl.*(..))")
  public void twilioPointCut() {
  }

}
