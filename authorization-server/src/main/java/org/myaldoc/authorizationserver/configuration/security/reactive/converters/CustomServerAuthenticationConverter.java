package org.myaldoc.authorizationserver.configuration.security.reactive.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.myaldoc.authorizationserver.connection.models.User;
import org.myaldoc.authorizationserver.connection.services.SignInUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.stream.Collectors;

@Profile("reactiveSecurity")
@Component
public class CustomServerAuthenticationConverter implements ServerAuthenticationConverter {

  @Autowired
  private ObjectMapper mapper;
  @Autowired
  private SignInUtils signInUtils;

  @Override
  public Mono<Authentication> convert(ServerWebExchange exchange) {
    return exchange.getRequest().getBody()
            .next()
            .flatMap(dataBuffer -> {
              try {
                User user = mapper.readValue(dataBuffer.asInputStream(), User.class);
                return Mono.just(user);
              } catch (IOException e) {
                return Mono.error(e);
              }
            })
            .map(user -> signInUtils.generateOAuth2Authentication(user,
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),
                            user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toSet()))));
  }
}
