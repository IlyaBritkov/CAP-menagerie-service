package com.leverx.menagerie.repository;

import cds.gen.com.leverx.menagerie.Dogs;
import cds.gen.petservice.DogsPetsView;

import java.util.List;
import java.util.Optional;

public interface DogRepository {

    Optional<DogsPetsView> findById(Integer id);

    Dogs save(Dogs newDog);

    List<Dogs> save(List<Dogs> newDogsList);

}
