package org.myaldoc.gestionpersonnesservice.api.models.comparators;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.myaldoc.core.utils.ConcatUtils;
import org.myaldoc.gestionpersonnesservice.api.models.Adresse;
import org.myaldoc.gestionpersonnesservice.api.models.PersonnePhysique;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 24/12/2018
 * @Class purposes : .......
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompareKeyGenerator {

    public static String generateAdresseKey(Adresse adresse) {
        return ConcatUtils.concat(
                adresse.getNumero(),
                adresse.getRue(),
                adresse.getVille(),
                adresse.getCodePostal()
        );
    }

    public static String generatePersonnePhysiqueKey(PersonnePhysique pers) {
        return ConcatUtils.concat(
                pers.getIdentite().getNom(),
                pers.getIdentite().getPrenom(),
                pers.getIdentite().getDateNaissance().toString()
        );
    }
}
