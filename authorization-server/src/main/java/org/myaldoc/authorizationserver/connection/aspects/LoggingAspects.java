package org.myaldoc.authorizationserver.connection.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 09/12/2018
 * @Class purposes : .......
 */
@Component
@Aspect
@Slf4j
public class LoggingAspects {

    @Before("servicesPointCut()")
    public void logBefore(JoinPoint point) {
        final String name = point.getSignature().getName();
        this.log("METHOD " + this.toUpperCase(name) + " STARTS");
    }

    @AfterReturning("servicesPointCut()")
    public void logAfterReturning(JoinPoint point) {
        final String name = point.getSignature().getName();
        this.log("METHOD " + this.toUpperCase(name) + " ENDS");
    }

    @AfterThrowing(value = "servicesPointCut()", throwing = "error")
    public void logAfterThrowing(JoinPoint point, Throwable error) {
        final String name = point.getSignature().getName();
        this.log("METHODS " + this.toUpperCase(name) + " FAILS WITH THE FOLLOWING ERROR MESSAGE : ");
        log.error(error.getMessage());
    }

    @Pointcut("execution(public * org.myaldoc.authorizationserver.connection.services.impl.*.*(..))")
    public void servicesPointCut() {
    }

    private void log(String message) {

        final int length = message.length();
        String str1 = "************************************";
        String str2 = "**************";
        for (int i = 0; i < length; i++) {
            str1 = str1 + "*";
        }

        log.info(str1);
        log.info(str2 + "    " + message + "    " + str2);
        log.info(str1);
    }

    private String toUpperCase(String source) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < source.length(); i++) {
            final char letter = source.charAt(i);
            if (Character.isUpperCase(source.charAt(i))) builder.append("_");
            builder.append(letter);
        }
        return builder.toString().toUpperCase();
    }
}
