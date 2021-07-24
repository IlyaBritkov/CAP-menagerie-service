package com.leverx.menagerie.service;

import cds.gen.petservice.DogsPetsView;
import com.leverx.menagerie.dto.request.create.DogCreateRequestDTO;
import com.leverx.menagerie.dto.request.update.DogUpdateRequestDTO;

import java.util.List;

public interface DogService {

    DogsPetsView findDogPetViewById(String id);

    DogsPetsView createDog(DogCreateRequestDTO dogDTO);

    List<DogsPetsView> createDog(List<DogCreateRequestDTO> dogDTOList);

    DogsPetsView updateDog(DogUpdateRequestDTO requestDogDTO);
}
