package org.myaldoc.authorizationserver.connexion.repository;

import org.myaldoc.authorizationserver.connexion.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {
  CustomUser findByUsername(String username);
}
