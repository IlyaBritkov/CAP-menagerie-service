package com.leverx.menagerie.repository;

import cds.gen.petservice.Pets;

import java.util.List;
import java.util.Optional;

public interface PetRepository {

    Optional<Pets> findById(String id);

    Pets save(Pets newPet);

    List<Pets> save(List<Pets> newPetsList);

    Pets update(Pets updatedPet);

    List<Pets> update(List<Pets> updatedPets);

    List<Pets> findAllByOwnerId(String ownerId);
}
