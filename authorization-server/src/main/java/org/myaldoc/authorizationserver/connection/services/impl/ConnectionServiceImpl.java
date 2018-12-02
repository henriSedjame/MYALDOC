package org.myaldoc.authorizationserver.connection.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.myaldoc.authorizationserver.connection.exceptions.ConnectionException;
import org.myaldoc.authorizationserver.connection.exceptions.ConnectionExceptionBuilder;
import org.myaldoc.authorizationserver.connection.exceptions.ConnectionExceptionMessages;
import org.myaldoc.authorizationserver.connection.messaging.EmailSource;
import org.myaldoc.authorizationserver.connection.models.Account;
import org.myaldoc.authorizationserver.connection.models.Role;
import org.myaldoc.authorizationserver.connection.models.User;
import org.myaldoc.authorizationserver.connection.repositories.AccountRepository;
import org.myaldoc.authorizationserver.connection.repositories.RoleRepository;
import org.myaldoc.authorizationserver.connection.repositories.UserRepository;
import org.myaldoc.authorizationserver.connection.services.ConnectionService;
import org.myaldoc.core.messaging.MyaldocEmailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.text.MessageFormat;

@Service
@EnableBinding(EmailSource.class)
@Slf4j
public class ConnectionServiceImpl implements ConnectionService {

  //********************************************************************************************************************
  // ATTRIBUTS
  //********************************************************************************************************************

  private UserRepository userRepository;
  private RoleRepository roleRepository;
  private AccountRepository accountRepository;
  private BCryptPasswordEncoder passwordEncoder;
  private ConnectionExceptionMessages exceptionMessages;
  private ConnectionExceptionBuilder exceptionBuilder;
  @Autowired
  private EmailSource emailSource;

  //********************************************************************************************************************
  // CONSTRUCTEUR
  //********************************************************************************************************************
  public ConnectionServiceImpl(UserRepository userRepository,
                               RoleRepository roleRepository,
                               AccountRepository accountRepository,
                               BCryptPasswordEncoder passwordEncoder,
                               ConnectionExceptionMessages exceptionMessages) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.accountRepository = accountRepository;
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
  public Mono<User> saveUser(User user) {
    user.setPassword(this.passwordEncoder.encode(user.getPassword()));
    return this.userRepository.insert(user);
  }

  /**
   * SAUVEGARDE D'UN ROLE
   * @param role
   * @return
   */
  @Override
  public Mono<Role> saveRole(Role role) {
    return this.roleRepository.insert(role);
  }

  /**
   * MISE A JOUR D'UN USER
   * @param user
   * @return
   */
  @Override
  public Mono<User> updateUser(User user) {
    return this.userRepository.save(user);
  }

  /**
   * AJOUT D'UN ROLE A UN USER
   * @param username
   * @param rolename
   * @return
   */
  @Override
  public Mono<User> addRoleToUser(String username, String rolename) {
    return Mono.zip(this.userRepository.findByUsername(username), this.roleRepository.findByRoleName(rolename))
            .flatMap(tuple -> {
              User user = tuple.getT1();
              Role role = tuple.getT2();
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
  public Mono<User> retrieveUser(String username) {
    return this.userRepository.findByUsername(username);
  }

  /**
   * CREATION D'UN COMPTE
   *
   * @param user
   * @return
   */
  @Override
  public Mono<Account> createNewAccount(@NotNull User user) {

    return Mono.defer(() -> this.userRepository.existsByUsername(user.getUsername()).flatMap(alreadyExist -> {
      if (alreadyExist)
        return Mono.error(this.exceptionBuilder.buildException(MessageFormat.format(this.exceptionMessages.getUserAlreadyExist(), user.getUsername()), null));

      user.setPassword(this.passwordEncoder.encode(user.getPassword()));

      this.sentForNotification(user);

      return this.accountRepository.insert(Account.builder()
              .user(user)
              .statut(Account.Statut.EN_ATTENTE_DE_CONFRIMATION)
              .build());
    }));
  }

  /**
   * SUPPRESSION D'UN COMPTE
   *
   * @param account
   */
  @Override
  public void deleteAccount(Account account) {
    @NotNull User user = account.getUser();

    this.userRepository.delete(user).subscribe(null, null, () -> {
      this.accountRepository.delete(account).subscribe();
    });
  }


  private void sentForNotification(User user) {
    emailSource.emailOutput().send(MessageBuilder
            .withPayload(
                    MyaldocEmailMessage.builder()
                            .sentToName(user.getUsername())
                            .sentToEmail(user.getEmail())
                            .build())
            .build());
  }

}
