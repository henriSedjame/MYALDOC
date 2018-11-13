package org.myaldoc.authorizationserver.connexion.service;

import org.myaldoc.authorizationserver.connexion.model.CustomRole;
import org.myaldoc.authorizationserver.connexion.model.CustomUser;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.stream.Stream;

@Component
public class DBSeeder {

  private ConnectionService myService;

  public DBSeeder(ConnectionService myService) {
    this.myService = myService;
  }

  @PostConstruct
  public void loadData() {
    Stream.of(new CustomUser(null, "henri", "henri", new ArrayList<>()),
            new CustomUser(null, "chloe", "chloe", new ArrayList<>()))
            .forEach(this.myService::saveUser);

    Stream.of(new CustomRole(null, CustomRole.ADMIN),
            new CustomRole(null, CustomRole.USER))
            .forEach(this.myService::saveRole);

    this.myService.addRoleToUser("henri", CustomRole.ADMIN);
    this.myService.addRoleToUser("henri", CustomRole.USER);
    this.myService.addRoleToUser("chloe", CustomRole.USER);
  }
}
