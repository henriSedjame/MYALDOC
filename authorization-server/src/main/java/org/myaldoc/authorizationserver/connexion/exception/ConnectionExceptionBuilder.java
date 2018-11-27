package org.myaldoc.authorizationserver.connexion.exception;

import org.myaldoc.core.exceptions.ExceptionBuilder;

/**
 * @Project authorization-server
 * @Author Henri Joel SEDJAME
 * @Date 27/11/2018
 * @Class purposes : .......
 */
public class ConnectionExceptionBuilder extends ExceptionBuilder<ConnectionException> {

    public ConnectionExceptionBuilder(Class<ConnectionException> exceptionClass) {
        super(exceptionClass);
    }
}
