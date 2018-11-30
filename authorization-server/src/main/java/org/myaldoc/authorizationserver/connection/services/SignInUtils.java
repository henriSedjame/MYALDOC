package org.myaldoc.authorizationserver.connection.services;

import lombok.extern.slf4j.Slf4j;
import org.myaldoc.authorizationserver.configuration.models.CustomOAuth2ClientDetails;
import org.myaldoc.authorizationserver.connection.models.User;
import org.myaldoc.core.utils.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Profile("webFlux")
@Component
@Slf4j
public class SignInUtils {

  @Autowired
  ServerSecurityContextRepository securityContextRepository;
  @Autowired
  CustomOAuth2ClientDetails oAuth2ClientDetails;
  @Autowired
  private ReactiveAuthenticationManager authenticationManager;
  @Autowired
  private AuthorizationServerTokenServices tokenServices;

  public Mono<OAuth2AccessToken> signIn(User user, ServerWebExchange exchange) {
    return Mono.just(user)
            .flatMap(u -> {
              UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                      u.getUsername(), u.getPassword(),
                      user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getRoleName())).collect(Collectors.toList())
              );
              OAuth2Authentication auth2Authentication = generateOAuth2Authentication(user, token);
              OAuth2AccessToken accessToken = tokenServices.createAccessToken(auth2Authentication);

              return authenticationManager
                      .authenticate(auth2Authentication)
                      .doOnError(e -> log.error(e.getMessage()))
                      .flatMap(auth -> {
                        SecurityContextImpl securityContext = new SecurityContextImpl(auth);
                        securityContextRepository
                                .save(exchange, securityContext)
                                .subscriberContext(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)));
                        return Mono.just(accessToken);
                      });
            });
  }

  public OAuth2Authentication generateOAuth2Authentication(User user, UsernamePasswordAuthenticationToken token) {
    OAuth2Request request = this.generateRequest(user, oAuth2ClientDetails);
    return new OAuth2Authentication(request, token);
  }

  public OAuth2Request generateRequest(User user, CustomOAuth2ClientDetails details) {

    Map<String, String> params = new HashMap<>();

    Map<String, Serializable> extensionProperties = new HashMap<>();

    Set<String> scopes = IterableUtils.StringToSet(details.getScopes(), ",");

    Set<String> resourceIds = IterableUtils.StringToSet(details.getResourceIds(), ",");

    Set<String> responseTypes = new HashSet<>();
    responseTypes.add("code");

    return new OAuth2Request(params, details.getClientId(),
            user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getRoleName())).collect(Collectors.toList()), true,
            scopes, resourceIds, null,
            responseTypes, extensionProperties);

  }

}
