package org.myaldoc.authorizationserver.connection.resources.mvc;

import org.myaldoc.authorizationserver.connection.models.User;
import org.myaldoc.authorizationserver.connection.services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Profile("webMvc")
@RestController
public class ConnectionController {

  @Autowired
  private ConnectionService connectionService;

  @GetMapping("/user/create")
  public Mono<User> createUser(@RequestBody User user) {
    return connectionService.saveUser(user);
  }

}
