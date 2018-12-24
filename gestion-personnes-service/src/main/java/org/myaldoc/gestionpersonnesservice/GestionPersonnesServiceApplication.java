package org.myaldoc.gestionpersonnesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.context.request.RequestContextListener;

@EnableDiscoveryClient
@SpringBootApplication
@EnableOAuth2Client
public class GestionPersonnesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionPersonnesServiceApplication.class, args);
    }

    @Bean
    RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

}

