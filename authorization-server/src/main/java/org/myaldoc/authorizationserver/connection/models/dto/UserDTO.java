package org.myaldoc.authorizationserver.connection.models.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@Builder
public class UserDTO {
  private String id;
  private String username;
  private String password;
  private String email;
  private List<String> roles = new ArrayList<>();
}
