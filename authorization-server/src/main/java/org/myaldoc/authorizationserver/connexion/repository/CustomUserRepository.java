package org.myaldoc.authorizationserver.connexion.repository;

import org.myaldoc.authorizationserver.connexion.model.CustomUser;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CustomUserRepository extends ReactiveMongoRepository<CustomUser, String> {

  Mono<CustomUser> findByUsername(String username);

  Mono<Boolean> existsByUsername(String username);
}
