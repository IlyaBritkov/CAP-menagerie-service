package com.leverx.menagerie.repository;

import cds.gen.com.leverx.menagerie.Cats;
import cds.gen.petservice.CatsPetsView;

import java.util.List;
import java.util.Optional;

public interface CatRepository {

    Optional<CatsPetsView> findCatPetViewById(String id);

    Cats save(Cats newCat);

    List<Cats> save(List<Cats> newCatsList);

    CatsPetsView update(CatsPetsView newCat);

    Cats update(Cats dog);
}
