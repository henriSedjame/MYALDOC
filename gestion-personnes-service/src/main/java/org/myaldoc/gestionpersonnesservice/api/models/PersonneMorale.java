package org.myaldoc.gestionpersonnesservice.api.models;

import lombok.*;
import org.myaldoc.gestionpersonnesservice.api.models.comparators.Comparators;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
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
@Document(collection = "personnes-morales")
public class PersonneMorale extends Personne {
    @Id
    private String id;
    @Valid
    @DBRef
    private InfosInsee infosInsee;
    @DBRef
    private Collection<@Valid PersonnePhysique> mandataires = new TreeSet<>(Comparators.PERSONNE_PHYSIQUE_COMPARATOR);
}
