package org.myaldoc.gestionpersonnesservice.api.models;

import lombok.*;
import org.myaldoc.core.constraints.Majeur;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 24/12/2018
 * @Class purposes : .......
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "identites")
public class Identite {
    @Id
    private String id;
    @NotNull
    private String nom;
    @NotNull
    private String prenom;
    @Past
    @Majeur
    @NotNull
    private LocalDate dateNaissance;
}
