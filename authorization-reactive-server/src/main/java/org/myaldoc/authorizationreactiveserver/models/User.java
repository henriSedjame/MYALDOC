package org.myaldoc.authorizationreactiveserver.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.myaldoc.core.security.models.MyaldocUser;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@NoArgsConstructor
public class User extends MyaldocUser {
  @Id
  private String id;

}
