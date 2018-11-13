package org.myaldoc.authorizationserver.connexion.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomRole {

  public static final String ADMIN = "ADMIN";
  public static final String USER = "USER";

  @Id
  @GeneratedValue
  private Long id;
  private String roleName;
}
