package org.myaldoc.proxyserver.controller;


import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.myaldoc.core.http.CustomHttpServletRequest;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@EnableResourceServer
public class UserController {

  @Autowired
  @Qualifier("oauth2ClientContext")
  private OAuth2ClientContext context;
  @Autowired
  private OAuth2RestTemplate restTemplate;


  @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
  public User retrieveConnectedUser(OAuth2Authentication auth, HttpServletRequest request) {
    OAuth2AccessToken accessToken = context.getAccessToken();
    CustomHttpServletRequest customRequest = new CustomHttpServletRequest(request);
    customRequest.addHeader("Authorization", "Bearer " + this.obtainToken());
    return User.builder()
            .username(auth.getName())
            .roles(auth.getAuthorities().stream().map(authority -> authority.getAuthority()).collect(Collectors.toList()))
            .token(accessToken)
            .hasValidToken(!accessToken.isExpired())
            .build();
  }

  @GetMapping(value = "/token", produces = MediaType.APPLICATION_JSON_VALUE)
  public String obtainToken() {
    Map<String, String> params = new HashMap<>();
    params.put("grant_type", "password");
    params.put("client_id", "test");
    params.put("username", "henri");
    params.put("password", "henri");
    Response response = RestAssured.given().auth().preemptive().basic("test", "test").and().with().params(params).when()
            .post("http://localhost:9000/auth/oauth/token");
    String access_token = this.get("access_token", response);
    String token_type = this.get("token-type", response);

    return access_token;
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

  public String get(String code, Response response) {
    return response.jsonPath().getString(code);
  }


}
