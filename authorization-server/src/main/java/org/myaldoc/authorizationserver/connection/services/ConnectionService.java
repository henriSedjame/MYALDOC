package org.myaldoc.authorizationserver.connection.services;

import org.myaldoc.authorizationserver.connection.models.Role;
import org.myaldoc.authorizationserver.connection.models.User;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface ConnectionService {

  Mono<User> saveUser(@Valid User user);

  Mono<Role> saveRole(@Valid Role role);

  Mono<User> updateUser(@Valid User user);

  Mono<User> activateUser(@NotNull String userId);

  Mono<User> addRoleToUser(@NotNull String username, @NotNull String rolename);

  Mono<User> retrieveUser(@NotNull String username);

  Mono<Void> deleteUser(@NotNull String userId);

}
