package org.myaldoc.proxyserver.exceptions;

import org.myaldoc.core.exceptions.ExceptionBuilder;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 23/12/2018
 * @Class purposes : .......
 */
public class ProxyExceptionBuilder extends ExceptionBuilder<ProxyException> {

    public ProxyExceptionBuilder(Class<ProxyException> exceptionClass) {
        super(exceptionClass);
    }
}
