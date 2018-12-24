package org.myaldoc.gestionpersonnesservice.api.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.Valid;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 24/12/2018
 * @Class purposes : .......
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Personne {
    @DBRef
    @Valid
    private Coordonnees coordonnees;
}
