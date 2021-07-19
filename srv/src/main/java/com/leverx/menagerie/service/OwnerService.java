package com.leverx.menagerie.service;

import cds.gen.petservice.Owners;


public interface OwnerService {

    Owners findEntityById(Integer id);
}
