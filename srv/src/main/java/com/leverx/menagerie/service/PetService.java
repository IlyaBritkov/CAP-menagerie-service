package com.leverx.menagerie.service;

import cds.gen.petservice.Pets;

public interface PetService {

    Pets findEntityById(Integer id);

    void exchangePets(Integer firstPetId, Integer secondPetId);
}
