package com.leverx.menagerie.service.impl;

import cds.gen.com.leverx.menagerie.Dogs;
import cds.gen.com.leverx.menagerie.Pets;
import com.leverx.menagerie.dto.request.create.DogCreateRequestDTO;
import com.leverx.menagerie.mapper.DogMapper;
import com.leverx.menagerie.repository.DogRepository;
import com.leverx.menagerie.service.DogService;
import com.leverx.menagerie.service.PetService;
import com.sap.cds.services.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.sap.cds.services.ErrorStatuses.NOT_FOUND;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor(onConstructor_ = @Autowired)

@Service
public class DogServiceImpl implements DogService {

    private final DogRepository dogRepository;

    private final PetService petService;

    private final DogMapper dogMapper;

    @Override
    public cds.gen.petservice.Dogs findDogById(Integer id) {
        Optional<cds.gen.petservice.Dogs> optionalDogById = dogRepository.findById(id);
        return optionalDogById.orElseThrow(() -> new ServiceException(NOT_FOUND,
                String.format("Dog by id=%d does not exist", id)));
    }

    @Override
    public Dogs createDog(DogCreateRequestDTO dogDTO) {
        petService.createPet(dogDTO);
        Dogs newDog = dogMapper.toEntity(dogDTO);
        return dogRepository.save(newDog);
    }

    @Override
    public List<Dogs> createDog(List<DogCreateRequestDTO> dogDTOList) {
        List<Pets> petsResult = petService.createPets(dogDTOList); // TODO: 7/21/2021
        List<Dogs> newDogsList = dogDTOList.stream()
                .map(dogMapper::toEntity)
                .collect(toList());

        List<Dogs> result = dogRepository.save(newDogsList);
        return result;
    }
}
