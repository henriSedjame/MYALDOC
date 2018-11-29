package org.myaldoc.authorizationserver.configuration.security.reactive;

import org.myaldoc.authorizationserver.configuration.security.mvc.handlers.ConnexionFailureHandler;
import org.myaldoc.authorizationserver.configuration.security.reactive.services.CustomReactiveUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Profile("reactiveSecurity")
@Configuration
@Order(1)
@EnableResourceServer
@EnableWebFluxSecurity
public class WebFluxSecurityConfig {


  @Autowired
  ConnexionFailureHandler failureHandler;
  @Autowired
  CustomReactiveUserDetailsService userDetailsService;
  @Autowired
  BCryptPasswordEncoder passwordEncoder;

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

    return http
            .csrf()
            .disable()

            .httpBasic()
            .disable()

            .authorizeExchange()
            .pathMatchers("/login", "/oauth/authorize")
            .permitAll()
            .anyExchange()
            .authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            //.authenticationFailureHandler(this.failureHandler)
            .and()
            .build();
  }

  @Bean
  public ReactiveAuthenticationManager reactiveAuthenticationManager() {
    UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
    authenticationManager.setPasswordEncoder(passwordEncoder);
    return authenticationManager;
  }


}
