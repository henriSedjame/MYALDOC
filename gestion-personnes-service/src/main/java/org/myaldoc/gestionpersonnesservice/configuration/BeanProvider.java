package org.myaldoc.gestionpersonnesservice.configuration;

import org.myaldoc.gestionpersonnesservice.exceptions.GestionPersonneException;
import org.myaldoc.gestionpersonnesservice.exceptions.GestionPersonneExceptionBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 24/12/2018
 * @Class purposes : .......
 */
@Configuration
public class BeanProvider {

    @Bean
    GestionPersonneExceptionBuilder exceptionBuilder() {
        return new GestionPersonneExceptionBuilder(GestionPersonneException.class);
    }
}
