package com.leverx.menagerie.repository;

import cds.gen.com.leverx.menagerie.Dogs;

import java.util.List;
import java.util.Optional;

public interface DogRepository {
    Optional<cds.gen.petservice.Dogs> findById(Integer id); // TODO: 7/21/2021 import

    Dogs save(Dogs newDog);

    List<Dogs> save(List<Dogs> newDogsList);
}
