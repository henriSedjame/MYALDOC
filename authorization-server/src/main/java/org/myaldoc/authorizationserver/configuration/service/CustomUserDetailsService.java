package org.myaldoc.authorizationserver.configuration.service;


import org.myaldoc.authorizationserver.connexion.repository.CustomUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  @Value("${error.message.usernotfound}")
  private String userNotfoundErrorMessage;

  private CustomUserRepository userRepository;

  public CustomUserDetailsService(CustomUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    return this.userRepository
            .findByUsername(username)
            .map(u -> new User(u.getUsername(), u.getPassword(), u.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList())))
            .blockOptional().orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format(userNotfoundErrorMessage, username)));
  }
}
