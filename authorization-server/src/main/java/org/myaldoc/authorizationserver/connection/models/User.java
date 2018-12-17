package org.myaldoc.authorizationserver.connection.models;

import lombok.*;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonSetter;
import org.myaldoc.authorizationserver.connection.comparators.Comparators;
import org.myaldoc.authorizationserver.connection.patterns.Patterns;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Set;
import java.util.TreeSet;

@Document(collection = "Users")
@Getter
@Setter
@ToString(exclude = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
  @Id
  private String id;
  @Indexed(unique = true)
  private String username;
  @Pattern(regexp = Patterns.PASSWORD)
  private String password;
  @Email
  @Indexed(unique = true)
  private String email;
  @DBRef
  private Set<Role> roles = new TreeSet<>(Comparators.ROLE_COMPARATOR);
  private boolean enabled;

  @JsonIgnore
  public String getPassword() {
    return password;
  }

  @JsonSetter
  public void setPassword(String password) {
    this.password = password;
  }
}
