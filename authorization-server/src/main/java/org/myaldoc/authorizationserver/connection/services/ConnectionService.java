package org.myaldoc.authorizationserver.connection.services;

import org.myaldoc.authorizationserver.connection.models.Role;
import org.myaldoc.authorizationserver.connection.models.User;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

public interface ConnectionService {

  Mono<User> saveUser(@Valid User user);

  Mono<Role> saveRole(@Valid Role role);

  Mono<User> updateUser(@Valid User user);

  Mono<User> addRoleToUser(String username, String rolename);

  Mono<User> retrieveUser(String username);

  Mono<Void> deleteUser(String userId);

}
