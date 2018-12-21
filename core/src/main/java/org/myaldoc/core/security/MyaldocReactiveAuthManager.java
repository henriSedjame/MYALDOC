package org.myaldoc.core.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class MyaldocReactiveAuthManager implements ReactiveAuthenticationManager {


  private JWTUtils jwtUtils;

  public MyaldocReactiveAuthManager(JWTUtils jwtUtils) {
    this.jwtUtils = jwtUtils;
  }

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {

    final String authToken = authentication.getCredentials().toString();

    String username;

    try {
      username = jwtUtils.getUsernameFromToken(authToken);
    } catch (Exception e) {
      username = null;
    }

    if (Objects.nonNull(username) && jwtUtils.validateToken(authToken)) {

      final Claims claims = jwtUtils.getAllClaimsFromToken(authToken);

      final List<String> roles = claims.get("role", List.class);

      final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username,
              null,
              roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

      return Mono.just(auth);
    }

    return Mono.empty();
  }
}
