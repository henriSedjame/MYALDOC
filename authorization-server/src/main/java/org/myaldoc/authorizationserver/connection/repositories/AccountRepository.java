package org.myaldoc.authorizationserver.connection.repositories;

import org.myaldoc.authorizationserver.connection.models.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AccountRepository extends ReactiveMongoRepository<Account, String> {
}
