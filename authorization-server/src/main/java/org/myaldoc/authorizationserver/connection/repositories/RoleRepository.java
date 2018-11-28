package org.myaldoc.authorizationserver.connection.repositories;

import org.myaldoc.authorizationserver.connection.models.Role;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import reactor.core.publisher.Mono;

@RepositoryRestResource(collectionResourceRel = "roles", path = "roles")
public interface RoleRepository extends ReactiveMongoRepository<Role, String> {

  Mono<Role> findByRoleName(String rolename);
}
