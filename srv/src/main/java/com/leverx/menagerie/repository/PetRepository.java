package com.leverx.menagerie.repository;

import cds.gen.petservice.Pets;

import java.util.List;
import java.util.Optional;

public interface PetRepository {

    Optional<Pets> findById(Integer id);

    Pets save(Pets newPet);

    List<Pets> save(List<Pets> newPetsList);

    void update(Pets updatedPet);

    void update(List<Pets> updatedPets);

}
