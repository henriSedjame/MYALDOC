package org.myaldoc.authorizationserver.connexion.service;

import org.myaldoc.authorizationserver.connexion.model.CustomRole;
import org.myaldoc.authorizationserver.connexion.model.CustomUser;
import reactor.core.publisher.Mono;

public interface ConnectionService {

  Mono<CustomUser> saveUser(CustomUser user);

  Mono<CustomRole> saveRole(CustomRole role);

  Mono<CustomUser> updateUser(CustomUser user);

  Mono<CustomUser> addRoleToUser(String username, String rolename);


}
