package org.myaldoc.authorizationserver.connexion.service.impl;

import org.myaldoc.authorizationserver.connexion.model.CustomRole;
import org.myaldoc.authorizationserver.connexion.model.CustomUser;
import org.myaldoc.authorizationserver.connexion.repository.CustomRoleRepository;
import org.myaldoc.authorizationserver.connexion.repository.CustomUserRepository;
import org.myaldoc.authorizationserver.connexion.service.ConnectionService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
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
  public CustomUser saveUser(CustomUser user) {
    user.setPassword(this.passwordEncoder.encode(user.getPassword()));
    return this.userRepository.save(user);
  }

  @Override
  public CustomRole saveRole(CustomRole role) {
    return this.roleRepository.save(role);
  }

  @Override
  public void addRoleToUser(String username, String rolename) {
    CustomUser user = this.userRepository.findByUsername(username);
    CustomRole role = this.roleRepository.findByRoleName(rolename);
    user.getRoles().add(role);

  }

}
