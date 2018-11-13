package org.myaldoc.authorizationserver;

import org.myaldoc.authorizationserver.connexion.comparator.Comparators;
import org.myaldoc.authorizationserver.connexion.model.CustomRole;
import org.myaldoc.authorizationserver.connexion.model.CustomUser;
import org.myaldoc.authorizationserver.connexion.repository.CustomRoleRepository;
import org.myaldoc.authorizationserver.connexion.repository.CustomUserRepository;
import org.myaldoc.authorizationserver.connexion.service.ConnectionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.TreeSet;
import java.util.stream.Stream;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthorizationServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(AuthorizationServerApplication.class, args);
  }

    @Bean
    CommandLineRunner employees(CustomUserRepository userRepository, CustomRoleRepository roleRepository, ConnectionService service) {
        return args -> {


            roleRepository
                    .deleteAll()
                    .subscribe(null, null, () -> {
                        Stream.of(
                                new CustomRole(null, CustomRole.ADMIN),
                                new CustomRole(null, CustomRole.USER)
                        ).forEach(role -> {
                            service
                                    .saveRole(role)
                                    .subscribe(System.out::println);
                        });

                        userRepository
                                .deleteAll()
                                .subscribe(null, null, () -> Stream.of(
                                        new CustomUser(null, "henri", "henri", new TreeSet<>(Comparators.ROLE_COMPARATOR)),
                                        new CustomUser(null, "chloe", "chloe", new TreeSet<>(Comparators.ROLE_COMPARATOR))
                                ).forEach(user -> {
                                    service
                                            .saveUser(user)
                                            .subscribe(u -> service.addRoleToUser(u.getUsername(), CustomRole.ADMIN));
                                }));
                    });

        };
    }
}
