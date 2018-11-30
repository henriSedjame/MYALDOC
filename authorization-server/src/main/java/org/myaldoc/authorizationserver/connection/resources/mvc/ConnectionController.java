package org.myaldoc.authorizationserver.connection.resources.mvc;

import org.myaldoc.authorizationserver.connection.models.Account;
import org.myaldoc.authorizationserver.connection.models.User;
import org.myaldoc.authorizationserver.connection.services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Profile("webMvc")
@RestController
public class ConnectionController {

  @Autowired
  private ConnectionService connectionService;

  @PostMapping(value = "/account/create", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Account> createAccount(@RequestBody User user) {
    return connectionService.createNewAccount(user);
  }

}
