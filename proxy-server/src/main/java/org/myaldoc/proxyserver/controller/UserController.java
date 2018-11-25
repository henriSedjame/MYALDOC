package org.myaldoc.proxyserver.controller;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@EnableResourceServer
public class UserController {

  @Autowired
  @Qualifier("oauth2ClientContext")
  private OAuth2ClientContext context;

  private OAuth2RestTemplate restTemplate;


  @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
  public User retrieveConnectedUser(OAuth2Authentication auth) {
    OAuth2AccessToken accessToken = context.getAccessToken();
    return User.builder()
            .username(auth.getName())
            .roles(auth.getAuthorities().stream().map(authority -> authority.getAuthority()).collect(Collectors.toList()))
            .token(accessToken)
            .hasValidToken(!accessToken.isExpired())
            .build();
  }


  @Builder
  @Getter
  @Setter
  public static class User {
    private String username;
    private String password;
    private List<String> roles = new ArrayList<>();
    private OAuth2AccessToken token;
    private boolean hasValidToken;
  }


}
