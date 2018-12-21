package org.myaldoc.core.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class MyaldocSecurityContextRepository implements ServerSecurityContextRepository {

  private static final String BEARER_ = "Bearer ";

  private MyaldocReactiveAuthManager authenticationManager;

  public MyaldocSecurityContextRepository(MyaldocReactiveAuthManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public Mono<SecurityContext> load(ServerWebExchange exchange) {

    final ServerHttpRequest request = exchange.getRequest();
    final String authorizationHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

    if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith(BEARER_)) {
      final String token = authorizationHeader.replace(BEARER_, "");
      final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(token, token);
      return this.authenticationManager
              .authenticate(authentication)
              .map(auth -> new SecurityContextImpl(auth));
    }

    return Mono.empty();
  }
}
