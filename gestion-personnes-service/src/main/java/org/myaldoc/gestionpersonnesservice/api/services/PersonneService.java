package org.myaldoc.gestionpersonnesservice.api.services;

import org.myaldoc.gestionpersonnesservice.api.models.Personne;
import reactor.core.publisher.Mono;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 24/12/2018
 * @Class purposes : .......
 */
public interface PersonneService<T extends Personne> {

    /**
     * Sauvegarder une personne
     **/
    Mono<T> savePersonne(T personne);

    /**
     * Mettre à jour une personne
     **/
    Mono<T> updatePersonne(T personne);

    /**
     * Supprimer une personne
     **/
    Mono<Void> deletePersonne(String personId);

}
