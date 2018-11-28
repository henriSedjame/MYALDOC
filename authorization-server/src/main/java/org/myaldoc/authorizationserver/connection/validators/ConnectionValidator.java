package org.myaldoc.authorizationserver.connection.validators;

import org.myaldoc.authorizationserver.connection.models.User;

import java.util.function.Predicate;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 27/11/2018
 * @Class purposes : .......
 */

public interface ConnectionValidator extends Predicate<User> {

}
