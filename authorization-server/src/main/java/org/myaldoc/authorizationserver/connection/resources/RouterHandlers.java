package org.myaldoc.authorizationserver.connection.resources;

import org.myaldoc.authorizationserver.connection.models.User;
import org.myaldoc.authorizationserver.connection.services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class RouterHandlers {

  @Autowired
  private ConnectionService connectionService;


    /**
     * CREATION D'UN NOUVEAU USER
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> handleCreateAccount(ServerRequest request) {
      return request.bodyToMono(User.class).flatMap(user -> this.connectionService.createNewAccount(user)
              .flatMap(account -> ServerResponse.ok().syncBody(account))
                .onErrorResume(error -> ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).syncBody(error.getMessage()))
        );
  }


}
