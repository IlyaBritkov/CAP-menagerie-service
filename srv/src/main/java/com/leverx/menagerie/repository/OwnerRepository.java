package com.leverx.menagerie.repository;

import cds.gen.petservice.Owners;

import java.util.Optional;

public interface OwnerRepository {

    Optional<Owners> findById(String id);

    boolean isOwnerByIdExists(String id);

    long deleteById(String id);
}
