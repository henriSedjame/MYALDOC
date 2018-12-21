package org.myaldoc.authorizationreactiveserver;

import org.myaldoc.authorizationreactiveserver.login.endpoints.AuthRequest;
import org.myaldoc.authorizationreactiveserver.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AuthorizationReactiveServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(AuthorizationReactiveServerApplication.class, args);
  }

  @Bean
  CommandLineRunner start(UserService service) {
    return args -> {
      service.saveUser(new AuthRequest("henri", "henri")).subscribe();
    };
  }

}

