package com.leverx.menagerie.service;

import cds.gen.petservice.Pets;
import com.leverx.menagerie.dto.request.create.DogCreateRequestDTO;

import java.util.List;

public interface PetService {

    Pets findEntityById(Integer id);

    cds.gen.com.leverx.menagerie.Pets createPet(DogCreateRequestDTO dogDTO);

    List<cds.gen.com.leverx.menagerie.Pets> createPets(List<DogCreateRequestDTO> dogDtoList);

    void exchangePets(Integer firstPetId, Integer secondPetId);
}
