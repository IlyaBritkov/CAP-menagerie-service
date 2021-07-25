package com.leverx.menagerie.repository;

import cds.gen.com.leverx.menagerie.Dogs;
import cds.gen.petservice.DogsPetsView;

import java.util.List;
import java.util.Optional;

public interface DogRepository {

    Optional<DogsPetsView> findDogPetViewById(String id);

    Dogs save(Dogs newDog);

    List<Dogs> save(List<Dogs> newDogsList);

    DogsPetsView update(DogsPetsView dog);

    Dogs update(Dogs dog);
}
