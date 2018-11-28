package org.myaldoc.authorizationserver.connection.updators;

import org.myaldoc.authorizationserver.connection.models.Account;
import org.springframework.stereotype.Component;

@Component
public interface AccountUpdator {

  Account updateStatut(Account account);
}
