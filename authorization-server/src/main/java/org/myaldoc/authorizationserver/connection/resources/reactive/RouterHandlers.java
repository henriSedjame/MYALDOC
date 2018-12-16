package org.myaldoc.authorizationserver.connection.resources.reactive;

import lombok.extern.slf4j.Slf4j;
import org.myaldoc.authorizationserver.connection.models.User;
import org.myaldoc.authorizationserver.connection.services.ConnectionService;
import org.myaldoc.authorizationserver.connection.services.SignInUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Profile("webFlux")
@Component
@Slf4j
public class RouterHandlers {

  //*************************************************************************
  //ATTRIBUTS
  //
  //*************************************************************************

  @Autowired
  private ConnectionService connectionService;
  @Autowired
  private SignInUtils signInUtils;

  //*************************************************************************
  //METHODS
  //
  //*************************************************************************

  //*************************************************************************
  //PRIVATE METHODS
  //
  //*************************************************************************
  private static Mono<? extends ServerResponse> handleError(Throwable error) {
    return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).syncBody(error.getMessage());
  }


  /**
   * CONNEXION
   *
   * @param request
   * @return
   */
  public Mono<ServerResponse> handleSignIn(ServerRequest request) {
    ServerWebExchange exchange = request.exchange();
    return request.bodyToMono(User.class)
            .flatMap(user -> this.signInUtils.signIn(user, exchange)
                    .flatMap(token -> ServerResponse.ok().syncBody(token))
                    .onErrorResume(RouterHandlers::handleError));
  }

}
