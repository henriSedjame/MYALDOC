package org.myaldoc.authorizationserver.connection.resources.mvc;

import lombok.extern.slf4j.Slf4j;
import org.myaldoc.authorizationserver.connection.models.User;
import org.myaldoc.authorizationserver.connection.services.ConnectionService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;

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

  @PutMapping(value = "/user/activate/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public void activateUser(@PathVariable("id") String userId, HttpServletResponse response) {
    this.connectionService.activateUser(userId)
            .subscribe(
                    user -> {
                      try {
                        response.sendRedirect(MessageFormat.format("/auth/activation?name={0}", user.getUsername()));
                      } catch (IOException e) {
                        log.error(e.getMessage());
                      }
                    },
                    e -> {
                      try {
                        response.sendRedirect(MessageFormat.format("/auth/activation?id={0}&error=true", userId));
                      } catch (IOException ex) {
                        log.error(ex.getMessage());
                      }
                    });
  }

}
