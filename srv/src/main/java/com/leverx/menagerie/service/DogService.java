package com.leverx.menagerie.service;

import cds.gen.com.leverx.menagerie.Dogs;
import com.leverx.menagerie.dto.request.create.DogCreateRequestDTO;

import java.util.List;

public interface DogService {

    cds.gen.petservice.Dogs findDogById(Integer id);

    Dogs createDog(DogCreateRequestDTO dogDTO);

    List<Dogs> createDog(List<DogCreateRequestDTO> dogDTOList);

}
