package com.leverx.menagerie.service;

import cds.gen.petservice.DogsPetsView;
import com.leverx.menagerie.dto.request.create.DogCreateRequestDTO;

import java.util.List;

public interface DogService {

    DogsPetsView findDogById(String id);

    DogsPetsView createDog(DogCreateRequestDTO dogDTO);

    List<DogsPetsView> createDog(List<DogCreateRequestDTO> dogDTOList);

}
