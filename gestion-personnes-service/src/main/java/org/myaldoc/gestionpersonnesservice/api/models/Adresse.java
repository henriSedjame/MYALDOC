package org.myaldoc.gestionpersonnesservice.api.models;

import lombok.*;
import org.myaldoc.core.patterns.Patterns;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Pattern;

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
@Document(collection = "adresses")
public class Adresse {
    @Id
    private String id;
    private String numero;
    private String rue;
    private String ville;
    @Pattern(regexp = Patterns.CODE_POSTAL)
    private String codePostal;
}
