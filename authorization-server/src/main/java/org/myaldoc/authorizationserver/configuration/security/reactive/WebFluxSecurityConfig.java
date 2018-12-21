package org.myaldoc.authorizationserver.configuration.security.reactive;

import org.myaldoc.authorizationserver.configuration.security.reactive.converters.CustomServerAuthenticationConverter;
import org.myaldoc.authorizationserver.configuration.security.reactive.services.CustomReactiveUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.logout.LogoutWebFilter;
import org.springframework.security.web.server.authentication.logout.SecurityContextServerLogoutHandler;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

@Profile("reactiveSecurity")
@Configuration
@EnableResourceServer
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity(proxyTargetClass = true)
public class WebFluxSecurityConfig {


  @Autowired
  CustomReactiveUserDetailsService userDetailsService;
  @Autowired
  BCryptPasswordEncoder passwordEncoder;
  @Autowired
  CustomServerAuthenticationConverter authenticationConverter;


  @Bean
  public ServerSecurityContextRepository serverSecurityContextRepository() {
    WebSessionServerSecurityContextRepository securityContextRepository = new WebSessionServerSecurityContextRepository();
    securityContextRepository.setSpringSecurityContextAttrName("securityContext");
    return securityContextRepository;
  }

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

    return http

            .csrf()
            .disable()

            .httpBasic()
            .disable()

            .formLogin()
            .disable()

            .logout()
            .disable()

            .securityContextRepository(serverSecurityContextRepository())

            .authorizeExchange()
            .anyExchange()
            .permitAll()
            .and()


            .addFilterAt(authenticationWebFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
            .addFilterAt(logoutWebFilter(), SecurityWebFiltersOrder.LOGOUT)
            .build();
  }

  @Bean
  public ReactiveAuthenticationManager reactiveAuthenticationManager() {
    UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
    authenticationManager.setPasswordEncoder(passwordEncoder);
    return authenticationManager;
  }

  private AuthenticationWebFilter authenticationWebFilter() {
    AuthenticationWebFilter filter = new AuthenticationWebFilter(reactiveAuthenticationManager());

    filter.setSecurityContextRepository(serverSecurityContextRepository());

    filter.setServerAuthenticationConverter(authenticationConverter);

    filter.setAuthenticationFailureHandler(null);
    filter.setAuthenticationSuccessHandler(null);
    filter.setRequiresAuthenticationMatcher(
            ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "/login")
    );
    return filter;
  }

  private LogoutWebFilter logoutWebFilter() {
    LogoutWebFilter logoutWebFilter = new LogoutWebFilter();

    SecurityContextServerLogoutHandler logoutHandler = new SecurityContextServerLogoutHandler();
    logoutHandler.setSecurityContextRepository(serverSecurityContextRepository());

    logoutWebFilter.setLogoutHandler(logoutHandler);
    logoutWebFilter.setLogoutSuccessHandler(null);
    logoutWebFilter.setRequiresLogoutMatcher(
            ServerWebExchangeMatchers.pathMatchers(HttpMethod.GET, "/logout")
    );

    return logoutWebFilter;
  }
}
