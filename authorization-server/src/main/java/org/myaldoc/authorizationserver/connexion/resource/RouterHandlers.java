package org.myaldoc.authorizationserver.connexion.resource;

import org.myaldoc.authorizationserver.connexion.model.CustomUser;
import org.myaldoc.authorizationserver.connexion.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class RouterHandlers {

  @Autowired
  private ConnectionService connectionService;

  /**
   * CREATION COMPTE
   **/

  public Mono<CustomUser> createAccount(ServerRequest request) {

    Mono<CustomUser> user = request.bodyToMono(CustomUser.class);

    return Mono.defer(() -> user.flatMap(

            u -> {

              Boolean alreadyExistingUser = connectionService.findByUserame(u.getUsername()).as(us -> Objects.nonNull(us));

              if (alreadyExistingUser) return Mono.error(new RuntimeException(""));

              return Mono.just(u);
            })
    );
  }


}
