package org.myaldoc.authorizationserver.connexion.resource;

import org.myaldoc.authorizationserver.connexion.model.CustomUser;
import org.myaldoc.authorizationserver.connexion.service.ConnectionService;
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
        return request.bodyToMono(CustomUser.class).flatMap(user -> this.connectionService.createNewUser(user)
                .flatMap(u -> ServerResponse.ok().syncBody(u))
                .onErrorResume(error -> ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).syncBody(error.getMessage()))
        );
  }


}
