package com.leverx.menagerie.repository;

import cds.gen.petservice.Owners;

import java.util.Optional;

public interface OwnerRepository {

    Optional<Owners> findById(Integer id);

    boolean isOwnerByIdExists(Integer id);

}
