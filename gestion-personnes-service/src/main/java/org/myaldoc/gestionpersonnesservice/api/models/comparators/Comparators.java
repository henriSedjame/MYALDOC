package org.myaldoc.gestionpersonnesservice.api.models.comparators;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.myaldoc.gestionpersonnesservice.api.models.Adresse;
import org.myaldoc.gestionpersonnesservice.api.models.PersonnePhysique;

import java.util.Comparator;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 24/12/2018
 * @Class purposes : .......
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Comparators {
    public static final Comparator<Adresse> ADRESSE_COMPARATOR = Comparator.comparing(CompareKeyGenerator::generateAdresseKey);
    public static final Comparator<PersonnePhysique> PERSONNE_PHYSIQUE_COMPARATOR = Comparator.comparing(CompareKeyGenerator::generatePersonnePhysiqueKey);
}
