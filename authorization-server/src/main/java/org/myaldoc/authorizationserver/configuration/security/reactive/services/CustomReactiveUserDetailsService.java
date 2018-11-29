package org.myaldoc.authorizationserver.configuration.security.reactive.services;

import org.myaldoc.authorizationserver.connection.repositories.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Profile("reactiveSecurity")
@Service
public class CustomReactiveUserDetailsService implements ReactiveUserDetailsService {

  private UserRepository userRepository;

  public CustomReactiveUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Mono<UserDetails> findByUsername(String username) {
    return this.userRepository
            .findByUsername(username)
            .map(u -> new User(u.getUsername(), u.getPassword(), u.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList())));
  }
}
