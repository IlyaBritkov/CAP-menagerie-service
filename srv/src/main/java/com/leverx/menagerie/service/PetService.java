package com.leverx.menagerie.service;

import cds.gen.petservice.Pets;
import com.leverx.menagerie.dto.request.create.DogCreateRequestDTO;

import java.util.List;

public interface PetService {

    Pets findEntityById(String id);

    Pets createPet(DogCreateRequestDTO dogDTO);

    List<Pets> createPets(List<DogCreateRequestDTO> dogDtoList);

    void exchangePets(String firstPetId, String secondPetId);

}
