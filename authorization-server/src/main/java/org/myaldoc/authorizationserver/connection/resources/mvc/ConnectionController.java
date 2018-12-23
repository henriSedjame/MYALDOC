package org.myaldoc.authorizationserver.connection.resources.mvc;

import lombok.extern.slf4j.Slf4j;
import org.myaldoc.authorizationserver.connection.models.User;
import org.myaldoc.authorizationserver.connection.services.ConnectionService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Profile("webMvc")
@RestController
@Slf4j
public class ConnectionController {


  private ConnectionService connectionService;

  public ConnectionController(ConnectionService connectionService) {
    this.connectionService = connectionService;
  }

  @PostMapping(value = "/user/create", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<User> createUser(@RequestBody User user) {
    return connectionService.saveUser(user);
  }

  @DeleteMapping("/user/delete/{id}")
  public Mono<Void> deleteUser(@PathVariable("id") String userId) {
    return this.connectionService.deleteUser(userId);
  }

  @GetMapping(value = "/user/activate/{id}")
  public Mono<User> activateUser(@PathVariable("id") String userId) {
    return this.connectionService.activateUser(userId);
  }

    @GetMapping(value = "/user/retrieve")
    public Mono<User> retrieveUser(@RequestParam(value = "username") String username) {
        return this.connectionService.retrieveUser(username);
    }

}
