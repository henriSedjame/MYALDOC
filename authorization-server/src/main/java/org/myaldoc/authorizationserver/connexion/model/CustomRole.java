package org.myaldoc.authorizationserver.connexion.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomRole {

  public static final String ADMIN = "ADMIN";
  public static final String USER = "USER";

  @Id
  private String id;
  private String roleName;
}
