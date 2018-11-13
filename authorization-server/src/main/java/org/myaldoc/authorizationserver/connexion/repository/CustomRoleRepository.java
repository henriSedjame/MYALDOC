package org.myaldoc.authorizationserver.connexion.repository;

import org.myaldoc.authorizationserver.connexion.model.CustomRole;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import reactor.core.publisher.Mono;

@RepositoryRestResource(collectionResourceRel = "roles", path = "roles")
public interface CustomRoleRepository extends ReactiveMongoRepository<CustomRole, String> {

  Mono<CustomRole> findByRoleName(String rolename);
}
