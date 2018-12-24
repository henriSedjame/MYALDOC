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
@Document(collection = "infosInsees")
public class InfosInsee {
    @Id
    private String id;
    @NotNull
    @Pattern(regexp = Patterns.SIRET)
    private String siret;
    @NotNull
    private String raisonSociale;
    private String logo;
}
