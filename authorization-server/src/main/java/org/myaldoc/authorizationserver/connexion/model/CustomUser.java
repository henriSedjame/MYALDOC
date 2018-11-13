package org.myaldoc.authorizationserver.connexion.model;

import lombok.*;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonSetter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Document(collection = "Users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomUser {
  @Id
  private String id;
  private String username;
  private String password;
  @DBRef
  private Collection<CustomRole> roles;

  @JsonIgnore
  public String getPassword() {
    return password;
  }

  @JsonSetter
  public void setPassword(String password) {
    this.password = password;
  }
}
