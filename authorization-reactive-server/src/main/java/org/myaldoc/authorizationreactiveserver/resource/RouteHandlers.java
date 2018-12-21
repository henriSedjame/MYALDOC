package org.myaldoc.authorizationreactiveserver.resource;

import org.myaldoc.authorizationreactiveserver.login.endpoints.AuthRequest;
import org.myaldoc.authorizationreactiveserver.login.endpoints.AuthResponse;
import org.myaldoc.authorizationreactiveserver.services.UserService;
import org.myaldoc.core.security.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class RouteHandlers {

  @Autowired
  private UserService userService;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private JWTUtils jwtUtils;

  private static Mono<? extends ServerResponse> handleError(Throwable error) {
    return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).syncBody(error.getMessage());
  }

  public Mono<ServerResponse> handleLogin(ServerRequest request) {

    return request.bodyToMono(AuthRequest.class)
            .flatMap(authRequest -> userService.retrieveUser(authRequest.getUsername())
                    .flatMap(user -> {
                      final String password = authRequest.getPassword();
                      if (passwordEncoder.encode(password).equals(user.getPassword())) {
                        return ServerResponse
                                .ok()
                                .syncBody(new AuthResponse(jwtUtils.generateToken(user)));
                      }
                      return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).syncBody("User not Found");
                    })
                    .onErrorResume(RouteHandlers::handleError)
            );
  }

}
