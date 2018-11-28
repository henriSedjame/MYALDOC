package org.myaldoc.authorizationserver.connection.updators.impl;

import org.myaldoc.authorizationserver.connection.models.Account;
import org.myaldoc.authorizationserver.connection.updators.AccountUpdator;

import java.util.Objects;

public class AccountUpdatorImpl implements AccountUpdator {


  @Override
  public Account updateStatut(Account account) {
    if (Objects.isNull(account.getId())) account.setStatut(Account.Statut.EN_ATTENTE_DE_CONFRIMATION);
    return account;
  }
}
