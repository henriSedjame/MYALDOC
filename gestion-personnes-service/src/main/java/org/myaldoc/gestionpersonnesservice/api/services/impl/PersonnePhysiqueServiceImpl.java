package org.myaldoc.gestionpersonnesservice.api.services.impl;

import org.myaldoc.core.aspects.annotations.ExceptionBuilderClearBefore;
import org.myaldoc.gestionpersonnesservice.api.models.PersonnePhysique;
import org.myaldoc.gestionpersonnesservice.api.repositories.PersonnePhysiqueRepository;
import org.myaldoc.gestionpersonnesservice.api.services.PersonneService;
import org.myaldoc.gestionpersonnesservice.exceptions.GestionPersonneExceptionBuilder;
import org.myaldoc.gestionpersonnesservice.exceptions.GestionPersonneExceptionMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @Project MYALDOC
 * @Author Henri Joel SEDJAME
 * @Date 24/12/2018
 * @Class purposes : .......
 */
@Service
public class PersonnePhysiqueServiceImpl implements PersonneService<PersonnePhysique> {

    //********************************************************************************************************************
    // ATTRIBUTS
    //********************************************************************************************************************
    private final PersonnePhysiqueRepository personneRepository;
    private final GestionPersonneExceptionBuilder exceptionBuilder;
    private final GestionPersonneExceptionMessages exceptionMessages;

    //********************************************************************************************************************
    // CONSTRUCTEUR
    //********************************************************************************************************************
    @Autowired
    public PersonnePhysiqueServiceImpl(PersonnePhysiqueRepository personneRepository,
                                       GestionPersonneExceptionBuilder exceptionBuilder,
                                       GestionPersonneExceptionMessages exceptionMessages) {
        this.personneRepository = personneRepository;
        this.exceptionBuilder = exceptionBuilder;
        this.exceptionMessages = exceptionMessages;
    }

    //********************************************************************************************************************
    // METHODES
    //********************************************************************************************************************

    /**
     * Sauvegarder une personne
     *
     * @param personne
     **/
    @Override
    @ExceptionBuilderClearBefore
    public Mono<PersonnePhysique> savePersonne(PersonnePhysique personne) {
        return this.personneRepository.insert(personne);
    }

    /**
     * Mettre à jour une personne
     *
     * @param personne
     **/
    @Override
    public Mono<PersonnePhysique> updatePersonne(PersonnePhysique personne) {
        return null;
    }

    /**
     * Supprimer une personne
     *
     * @param personId
     **/
    @Override
    public Mono<Void> deletePersonne(String personId) {
        return null;
    }
}
