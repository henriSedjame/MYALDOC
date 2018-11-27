package org.myaldoc.authorizationserver.connexion.service.impl;

import org.myaldoc.authorizationserver.connexion.exception.ConnectionException;
import org.myaldoc.authorizationserver.connexion.exception.ConnectionExceptionBuilder;
import org.myaldoc.authorizationserver.connexion.exception.ConnectionExceptionMessages;
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

  //********************************************************************************************************************
  // ATTRIBUTS
  //********************************************************************************************************************

  private CustomUserRepository userRepository;
  private CustomRoleRepository roleRepository;
  private BCryptPasswordEncoder passwordEncoder;
  private ConnectionExceptionMessages exceptionMessages;
  private ConnectionExceptionBuilder exceptionBuilder;


  //********************************************************************************************************************
  // CONSTRUCTEUR
  //********************************************************************************************************************
  public ConnectionServiceImpl(CustomUserRepository userRepository,
                               CustomRoleRepository roleRepository,
                               BCryptPasswordEncoder passwordEncoder,
                               ConnectionExceptionMessages exceptionMessages) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
    this.exceptionMessages = exceptionMessages;
    this.exceptionBuilder = new ConnectionExceptionBuilder(ConnectionException.class);
  }

  //********************************************************************************************************************
  // METHODES
  //********************************************************************************************************************

  /**
   * SAUVEGARDE D'UN USER
   *
   * @param user
   * @return
   */
  @Override
  public Mono<CustomUser> saveUser(CustomUser user) {
    user.setPassword(this.passwordEncoder.encode(user.getPassword()));
    return this.userRepository.insert(user);
  }

  /**
   * SAUVEGARDE D'UN ROLE
   * @param role
   * @return
   */
  @Override
  public Mono<CustomRole> saveRole(CustomRole role) {
    return this.roleRepository.insert(role);
  }

  /**
   * MISE A JOUR D'UN USER
   * @param user
   * @return
   */
  @Override
  public Mono<CustomUser> updateUser(CustomUser user) {
    return this.userRepository.save(user);
  }

  /**
   * AJOUT D'UN ROLE A UN USER
   * @param username
   * @param rolename
   * @return
   */
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

  /**
   * RECHERCHER UN USER
   * @param username
   * @return
   */
  @Override
  public Mono<CustomUser> retrieveUser(String username) {
    return this.userRepository.findByUsername(username);
  }

  /**
   * CREATION D'UN COMPTE
   *
   * @param user
   * @return
   */
  @Override
  public Mono<CustomUser> createNewUser(CustomUser user) {
    return Mono.defer(() -> this.userRepository.existsByUsername(user.getUsername()).flatMap(alreadyExist -> {
      if (alreadyExist)
        return Mono.error(this.exceptionBuilder.buildException(this.exceptionMessages.getUserAlreadyExist(), null));
      return this.userRepository.save(user);
    }));

  }

}
