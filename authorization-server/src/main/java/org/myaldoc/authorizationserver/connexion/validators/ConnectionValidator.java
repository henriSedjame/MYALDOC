package org.myaldoc.authorizationserver.connexion.validators;

import org.myaldoc.authorizationserver.connexion.model.CustomUser;

import java.util.function.Predicate;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 27/11/2018
 * @Class purposes : .......
 */

public interface ConnectionValidator extends Predicate<CustomUser> {

}
