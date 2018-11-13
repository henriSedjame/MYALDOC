package org.myaldoc.authorizationserver.connexion.model;

import lombok.*;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonSetter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "Users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomUser {
  @Id
  @GeneratedValue
  private Long id;
  private String username;
  private String password;
  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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
