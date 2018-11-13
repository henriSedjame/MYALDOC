package org.myaldoc.authorizationserver.connexion.repository;

import org.myaldoc.authorizationserver.connexion.model.CustomUser;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import reactor.core.publisher.Mono;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface CustomUserRepository extends ReactiveMongoRepository<CustomUser, String> {

  Mono<CustomUser> findByUsername(String username);
}
