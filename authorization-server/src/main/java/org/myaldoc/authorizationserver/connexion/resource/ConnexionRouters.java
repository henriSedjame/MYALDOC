package org.myaldoc.authorizationserver.connexion.resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ConnexionRouters {

    @Bean
    RouterFunction<?> routerFunction(RouterHandlers handlers) {
        return route(RequestPredicates.GET(""), handlers::handleCreateAccount);
    }
}
