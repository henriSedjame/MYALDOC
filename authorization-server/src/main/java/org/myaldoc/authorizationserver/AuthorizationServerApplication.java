package org.myaldoc.authorizationserver;

import org.myaldoc.authorizationserver.connection.comparators.Comparators;
import org.myaldoc.authorizationserver.connection.models.Role;
import org.myaldoc.authorizationserver.connection.models.User;
import org.myaldoc.authorizationserver.connection.repositories.AccountRepository;
import org.myaldoc.authorizationserver.connection.repositories.RoleRepository;
import org.myaldoc.authorizationserver.connection.repositories.UserRepository;
import org.myaldoc.authorizationserver.connection.services.ConnectionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.TreeSet;
import java.util.stream.Stream;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthorizationServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(AuthorizationServerApplication.class, args);
  }


  @Bean
  @Profile("dev")
  CommandLineRunner employees(UserRepository userRepository, RoleRepository roleRepository, AccountRepository accountRepository, ConnectionService service) {
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

                accountRepository.findAll()
                        .subscribe(account -> service.deleteAccount(account),
                                null,
                                () -> Stream.of(
                                        new User(null, "henri", "henri", "sedhjodev@gmail.com", new TreeSet<>(Comparators.ROLE_COMPARATOR)),
                                        new User(null, "chloe", "chloe", "chloe@gmail.com", new TreeSet<>(Comparators.ROLE_COMPARATOR))
                                ).forEach(user -> {
                                  service
                                          .createNewAccount(user)
                                          .subscribe(account -> service.addRoleToUser(account.getUser().getUsername(), Role.ADMIN).subscribe(null, e -> System.out.println("ERREUR 1 " + e.getMessage())),
                                                  e -> e.printStackTrace()

                                          );
                                }));
              });

    };

  }
}
