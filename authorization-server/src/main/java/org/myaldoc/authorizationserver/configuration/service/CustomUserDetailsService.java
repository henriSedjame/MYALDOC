package org.myaldoc.authorizationserver.configuration.service;


import org.myaldoc.authorizationserver.connexion.model.CustomUser;
import org.myaldoc.authorizationserver.connexion.repository.CustomUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private CustomUserRepository userRepository;

  public CustomUserDetailsService(CustomUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    CustomUser user = this.userRepository.findByUsername(username);

    List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());

    return new User(user.getUsername(), user.getPassword(), authorities);
  }
}
