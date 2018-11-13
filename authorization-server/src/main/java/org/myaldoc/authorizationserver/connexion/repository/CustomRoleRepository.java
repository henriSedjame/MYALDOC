package org.myaldoc.authorizationserver.connexion.repository;

import org.myaldoc.authorizationserver.connexion.model.CustomRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "roles", path = "roles")
public interface CustomRoleRepository extends JpaRepository<CustomRole, Long> {
  CustomRole findByRoleName(String rolename);
}
