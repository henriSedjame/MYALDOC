package org.myaldoc.authorizationserver.connection.models.mappings;

import org.myaldoc.authorizationserver.connection.models.User;
import org.myaldoc.authorizationserver.connection.models.dto.UserDTO;


public interface UserMapper {
  UserDTO toDto(User user);

  User toEntity(UserDTO dto);
}
