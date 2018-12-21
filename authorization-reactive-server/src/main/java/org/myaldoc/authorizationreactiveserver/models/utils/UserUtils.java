package org.myaldoc.authorizationreactiveserver.models.utils;

import org.myaldoc.authorizationreactiveserver.login.endpoints.AuthRequest;
import org.myaldoc.authorizationreactiveserver.models.User;

public class UserUtils {

  public static User build(AuthRequest request) {
    User user = new User();
    user.setUsername(request.getUsername());
    user.setPassword(request.getPassword());
    return user;
  }
}
