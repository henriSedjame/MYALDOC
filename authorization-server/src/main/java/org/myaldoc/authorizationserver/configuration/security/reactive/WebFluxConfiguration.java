package org.myaldoc.authorizationserver.configuration.security.reactive;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.config.EnableWebFlux;

@Profile("reactiveSecurity")
@Configuration
@EnableWebFlux
public class WebFluxConfiguration {
}
