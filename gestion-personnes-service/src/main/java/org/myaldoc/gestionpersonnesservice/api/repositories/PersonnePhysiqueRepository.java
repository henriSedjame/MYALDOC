package org.myaldoc.gestionpersonnesservice.api.repositories;

import org.myaldoc.gestionpersonnesservice.api.models.PersonnePhysique;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 24/12/2018
 * @Class purposes : .......
 */
public interface PersonnePhysiqueRepository extends ReactiveMongoRepository<PersonnePhysique, String> {
}
