package org.myaldoc.authorizationreactiveserver.resource;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component
public class ConnectionRouters {

  @Bean
  RouterFunction<?> routerFunction(RouteHandlers handlers) {
    return route(POST("/login").and(accept(MediaType.APPLICATION_JSON)), handlers::handleLogin);
  }
}
