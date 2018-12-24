package org.myaldoc.gestionpersonnesservice.api.models;

import lombok.*;
import org.myaldoc.gestionpersonnesservice.mongo.cascade.operations.annotations.CascadeSave;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;

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
@Document(collection = "personnes-physiques")
public class PersonnePhysique extends Personne {
    @Id
    private String id;
    @Valid
    @DBRef
    @CascadeSave
    private Identite identite;
}
