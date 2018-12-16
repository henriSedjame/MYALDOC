package org.myaldoc.authorizationserver;

import org.myaldoc.authorizationserver.connection.models.Role;
import org.myaldoc.authorizationserver.connection.repositories.RoleRepository;
import org.myaldoc.authorizationserver.connection.repositories.UserRepository;
import org.myaldoc.authorizationserver.connection.services.ConnectionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.stream.Stream;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthorizationServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(AuthorizationServerApplication.class, args);
  }


  @Bean
  @Profile("dev")
  CommandLineRunner employees(UserRepository userRepository, RoleRepository roleRepository, ConnectionService service) {
    return args -> {
      roleRepository
              .deleteAll()
              .subscribe(null, null, () -> {
                Stream.of(
                        new Role(null, Role.ADMIN),
                        new Role(null, Role.USER)
                ).forEach(role -> {
                  service
                          .saveRole(role)
                          .subscribe(System.out::println);
                });
              });

    };

  }
}
