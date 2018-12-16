package org.myaldoc.authorizationserver.connection.configuration;

import org.myaldoc.authorizationserver.connection.exceptions.ConnectionException;
import org.myaldoc.authorizationserver.connection.exceptions.ConnectionExceptionBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 15/12/2018
 * @Class purposes : .......
 */
@Configuration
public class BeanProvider {

    @Bean
    public ConnectionExceptionBuilder connectionExceptionBuilder() {
        return new ConnectionExceptionBuilder(ConnectionException.class);
    }
}
