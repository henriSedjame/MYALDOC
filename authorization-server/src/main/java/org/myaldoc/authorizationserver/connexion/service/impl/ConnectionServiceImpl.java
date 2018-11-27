package org.myaldoc.authorizationserver.connexion.service.impl;

import org.myaldoc.authorizationserver.connexion.model.CustomRole;
import org.myaldoc.authorizationserver.connexion.model.CustomUser;
import org.myaldoc.authorizationserver.connexion.repository.CustomRoleRepository;
import org.myaldoc.authorizationserver.connexion.repository.CustomUserRepository;
import org.myaldoc.authorizationserver.connexion.service.ConnectionService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ConnectionServiceImpl implements ConnectionService {

  private CustomUserRepository userRepository;
  private CustomRoleRepository roleRepository;
  private BCryptPasswordEncoder passwordEncoder;

  public ConnectionServiceImpl(CustomUserRepository userRepository, CustomRoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Mono<CustomUser> saveUser(CustomUser user) {
    user.setPassword(this.passwordEncoder.encode(user.getPassword()));
    return this.userRepository.insert(user);
  }

  @Override
  public Mono<CustomRole> saveRole(CustomRole role) {
    return this.roleRepository.insert(role);
  }

  @Override
  public Mono<CustomUser> updateUser(CustomUser user) {
    return this.userRepository.save(user);
  }

  @Override
  public Mono<CustomUser> addRoleToUser(String username, String rolename) {
    return Mono.zip(this.userRepository.findByUsername(username), this.roleRepository.findByRoleName(rolename))
            .flatMap(tuple -> {
              CustomUser user = tuple.getT1();
              CustomRole role = tuple.getT2();
              user.getRoles().add(role);
              return this.updateUser(user);
            });
  }

  @Override
  public Mono<CustomUser> findByUserame(String username) {
    return this.userRepository.findByUsername(username);
  }

}
