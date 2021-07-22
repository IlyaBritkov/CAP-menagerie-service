package com.leverx.menagerie.repository;

import cds.gen.petservice.Pets;

import java.util.List;
import java.util.Optional;

public interface PetRepository {

    Optional<Pets> findById(Integer id);

    cds.gen.com.leverx.menagerie.Pets save(cds.gen.com.leverx.menagerie.Pets newPet);

    List<cds.gen.com.leverx.menagerie.Pets> save(List<cds.gen.com.leverx.menagerie.Pets> newPetsList);

    void update(Pets updatedPet);

    void update(List<Pets> updatedPets);
}
