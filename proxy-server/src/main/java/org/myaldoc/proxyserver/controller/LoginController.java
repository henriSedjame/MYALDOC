package org.myaldoc.proxyserver.controller;


import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.myaldoc.proxyserver.exceptions.ProxyException;
import org.myaldoc.proxyserver.exceptions.ProxyExceptionBuilder;
import org.myaldoc.proxyserver.exceptions.ProxyExceptionMessages;
import org.myaldoc.proxyserver.login.endpoints.AuthRequest;
import org.myaldoc.proxyserver.login.endpoints.AuthResponse;
import org.myaldoc.proxyserver.security.config.Oauth2Details;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class LoginController {

  //********************************************************************************************************************
  // CONSTANTES
  //********************************************************************************************************************
  private static final String ACCESS_TOKEN = "access_token";
  private static final String TOKEN_TYPE = "token_type";
  private static final String EXPIRES_IN = "expires_in";
  private static final String GRANT_TYPE = "grant_type";
  private static final String CLIENT_ID = "client_id";
  private static final String USERNAME = "username";
  private static final String PASSWORD = "password";
  private final ProxyExceptionBuilder exceptionBuilder;
  private final ProxyExceptionMessages exceptionMessages;
  //********************************************************************************************************************
  // ATTRIBUTS
  //********************************************************************************************************************
  private Oauth2Details details;

  //********************************************************************************************************************
  // CONSTRUCTEUR
  //********************************************************************************************************************
  public LoginController(Oauth2Details details, ProxyExceptionMessages exceptionMessages) {
    this.details = details;
    this.exceptionBuilder = new ProxyExceptionBuilder(ProxyException.class);
    this.exceptionMessages = exceptionMessages;
  }

  //********************************************************************************************************************
  // REST ENDPOINTS
  //********************************************************************************************************************
  @PostMapping(value = "/token", consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest request) {
    Map<String, String> params = new HashMap<>();
    params.put(GRANT_TYPE, PASSWORD);
    params.put(CLIENT_ID, details.getClientId());
    params.put(USERNAME, request.getUsername());
    params.put(PASSWORD, request.getPassword());

    try {
      Response response = RestAssured.given()
              .auth()
              .preemptive()
              .basic(details.getClientId(), details.getClientSecret())
              .and()
              .with()
              .params(params)
              .when()
              .post(details.getAccessTokenUri());

      String token = this.get(ACCESS_TOKEN, response);
      String type = this.get(TOKEN_TYPE, response);
      String expires = this.get(EXPIRES_IN, response);

      if (Objects.isNull(token))
        return Mono.error(this.exceptionBuilder.buildException(this.exceptionMessages.getTokenRetrievingError(), null));

      return Mono.just(ResponseEntity.ok(AuthResponse.builder()
              .token(token)
              .type(type)
              .expirationTime(expires)
              .build()));

    } catch (Exception e) {
      return Mono.error(this.exceptionBuilder.buildException(this.exceptionMessages.getTokenRetrievingError(), e));
    }
  }

  //********************************************************************************************************************
  // PRIVATE METHODS
  //********************************************************************************************************************
  private String get(String code, Response response) {
    return response.jsonPath().getString(code);
  }

}
