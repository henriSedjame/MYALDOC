package org.myaldoc.authorizationserver.connexion.service;

import org.myaldoc.authorizationserver.connexion.model.CustomRole;
import org.myaldoc.authorizationserver.connexion.model.CustomUser;

public interface ConnectionService {

  CustomUser saveUser(CustomUser user);

  CustomRole saveRole(CustomRole role);

  void addRoleToUser(String username, String rolename);


}
