package com.leverx.menagerie.service;

import cds.gen.petservice.CatsPetsView;
import cds.gen.petservice.DogsPetsView;
import cds.gen.petservice.Pets;
import com.leverx.menagerie.dto.request.create.PetCreateRequestDTO;

import java.util.List;

public interface PetService {

    Pets findEntityById(String id);

    Pets createPet(PetCreateRequestDTO petDTO);

    List<Pets> createPets(List<? extends PetCreateRequestDTO> petDtoList);

    void exchangePets(String firstPetId, String secondPetId);

    Pets updatePet(DogsPetsView dogPetView);

    Pets updatePet(CatsPetsView catPetView);

    long deleteOwnerById(String ownerId);

    void removeOwnerFromPets(String ownerId);
}
