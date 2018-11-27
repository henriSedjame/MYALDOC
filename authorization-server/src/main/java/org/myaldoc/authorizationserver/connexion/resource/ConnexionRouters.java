package org.myaldoc.authorizationserver.connexion.resource;

import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

public class ConnexionRouters {

  @Bean
  RouterFunction<?> routerFunction(RouterHandlers handlers) {
    return RouterFunctions
            .route(RequestPredicates.GET(""), serverRequest -> {
              return null;
            })
            ;

  }
}
