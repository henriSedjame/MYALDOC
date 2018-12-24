package org.myaldoc.gestionpersonnesservice.api.models;

import lombok.*;
import org.myaldoc.gestionpersonnesservice.api.models.comparators.Comparators;
import org.myaldoc.gestionpersonnesservice.mongo.cascade.operations.annotations.CascadeSave;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.TreeSet;

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
@Document(collection = "coordonnees")
public class Coordonnees {
    @Id
    private String id;
    @DBRef
    @CascadeSave
    private Collection<@Valid Adresse> adresses = new TreeSet<>(Comparators.ADRESSE_COMPARATOR);
    @Valid
    @CascadeSave
    private Telephone telephone;
    @Email
    private String email;
}
