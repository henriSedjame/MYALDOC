package org.myaldoc.gestionpersonnesservice.security.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Project Spring cloud test
 * @Author Henri Joel SEDJAME
 * @Date 10/11/2018
 * @Class purposes : .......
 */
@Configuration
@ComponentScan({"org.myaldoc.gestionpersonnesservice.api"})
public class ResourceServerWebConfig implements WebMvcConfigurer {

}
