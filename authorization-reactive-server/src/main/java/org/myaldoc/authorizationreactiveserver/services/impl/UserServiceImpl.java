package org.myaldoc.authorizationreactiveserver.services.impl;

import org.myaldoc.authorizationreactiveserver.login.endpoints.AuthRequest;
import org.myaldoc.authorizationreactiveserver.models.User;
import org.myaldoc.authorizationreactiveserver.models.utils.UserUtils;
import org.myaldoc.authorizationreactiveserver.repository.UserRepository;
import org.myaldoc.authorizationreactiveserver.services.UserService;
import org.myaldoc.core.security.models.MyaldocRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;

  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Mono<User> saveUser(AuthRequest authRequest) {
    return this.userRepository
            .existsByUsername(authRequest.getUsername())
            .flatMap(exist -> {
              if (exist)
                return Mono.error(new RuntimeException(""));

              final User user = UserUtils.build(authRequest);
              user.setPassword(passwordEncoder.encode(user.getPassword()));
              user.getRoles().add(MyaldocRole.ROLE_USER);
              return this.userRepository.save(user);
            });
  }

  @Override
  public Mono<User> activateUser(String userId) {
    return null;
  }

  @Override
  public Mono<User> retrieveUser(String username) {
    return this.userRepository.findByUsername(username);
  }

  @Override
  public Mono<User> updateUser(User user) {
    return null;
  }

  @Override
  public Mono<Void> deleteUser(String userId) {
    return null;
  }
}
