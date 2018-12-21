package org.myaldoc.authorizationreactiveserver.security;


import org.myaldoc.core.security.JWTUtils;
import org.myaldoc.core.security.MyaldocPasswordEncoder;
import org.myaldoc.core.security.MyaldocReactiveAuthManager;
import org.myaldoc.core.security.MyaldocSecurityContextRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;

@Configuration
public class GlobalConfiguration {

  @Bean
  JWTUtils jwtUtils() {
    return new JWTUtils();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new MyaldocPasswordEncoder();
  }

  @Bean
  MyaldocReactiveAuthManager myaldocAuthenticationManager() {
    return new MyaldocReactiveAuthManager(jwtUtils());
  }

  @Bean
  ServerSecurityContextRepository myaldocSecurityContextRepository() {
    return new MyaldocSecurityContextRepository(myaldocAuthenticationManager());
  }
}
