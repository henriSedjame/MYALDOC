package org.myaldoc.authorizationserver.connection.resources.reactive;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Profile("webFlux")
@Configuration
public class ConnectionRouters {

    @Bean
    RouterFunction<?> routerFunction(RouterHandlers handlers) {
        return route(POST("/account/create").and(accept(MediaType.APPLICATION_JSON)), handlers::handleCreateAccount)
                .andRoute(POST("/login").and(accept(MediaType.APPLICATION_JSON)), handlers::handleSignIn);
    }
}
