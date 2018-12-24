package org.myaldoc.gestionpersonnesservice.api.models;

import lombok.*;
import org.myaldoc.core.patterns.Patterns;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
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
@Document(collection = "telephones")
public class Telephone {
    @Id
    private String id;
    @Pattern(regexp = Patterns.INDICATIF_TELEPHONE)
    private String indicatif;
    @Pattern(regexp = Patterns.NUM_TELEPHONE)
    @NotNull
    private String numero;
    private boolean verifie;

}
