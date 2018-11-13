package org.myaldoc.authorizationserver.connexion.comparator;

import org.myaldoc.authorizationserver.connexion.model.CustomRole;

import java.util.Comparator;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 13/11/2018
 * @Class purposes : .......
 */
public class Comparators {
    public static final Comparator<CustomRole> ROLE_COMPARATOR = Comparator.comparing(CustomRole::getRoleName);
}
