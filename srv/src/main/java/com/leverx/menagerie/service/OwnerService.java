package com.leverx.menagerie.service;

import cds.gen.petservice.Owners;


public interface OwnerService {

    Owners findEntityById(String id);

    boolean isOwnerByIdExists(String id);

    long deleteById(String ownerId);

}
