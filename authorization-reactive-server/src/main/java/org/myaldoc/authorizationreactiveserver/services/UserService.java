package org.myaldoc.authorizationreactiveserver.services;

import org.myaldoc.authorizationreactiveserver.login.endpoints.AuthRequest;
import org.myaldoc.authorizationreactiveserver.models.User;
import reactor.core.publisher.Mono;

public interface UserService {

  Mono<User> saveUser(AuthRequest user);

  Mono<User> activateUser(String userId);

  Mono<User> retrieveUser(String username);

  Mono<User> updateUser(User user);

  Mono<Void> deleteUser(String userId);

}
