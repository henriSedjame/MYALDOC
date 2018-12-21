package org.myaldoc.authorizationreactiveserver.security;

import org.myaldoc.core.security.MyaldocReactiveAuthManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig {

  @Autowired
  @Qualifier("myaldocAuthenticationManager")
  private MyaldocReactiveAuthManager authenticationManager;

  @Autowired
  @Qualifier("myaldocSecurityContextRepository")
  private ServerSecurityContextRepository securityContextRepository;

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    return http
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .authenticationManager(authenticationManager)
            .securityContextRepository(securityContextRepository)
            .authorizeExchange()
            .anyExchange().permitAll()
            .and().build();
  }

}
