package org.myaldoc.authorizationserver.connection.models;


import lombok.*;
import org.myaldoc.authorizationserver.connection.mongo.cascade.operations.annotations.CascadeSave;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Document(collection = "Accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
  @Id
  private String id;
  @DBRef
  @CascadeSave
  @NotNull
  private User user;
  @NotNull
  private Statut statut;
  @CreatedDate
  private LocalDate dateCreation;
  @LastModifiedDate
  private LocalDate dateLastModification;

  /** BLOC D'INITIALISATION **/ {
    statut = Statut.EN_CREATION;
  }

  /**
   * ENUM
   **/
  public enum Statut {
    EN_CREATION,
    EN_ATTENTE_DE_CONFRIMATION,
    CONFIRME,
    SUPPRIME;
  }

}
