package com.leverx.menagerie.service.impl;

import cds.gen.com.leverx.menagerie.Dogs;
import cds.gen.petservice.Pets;
import cds.gen.petservice.DogsPetsView;
import com.leverx.menagerie.dto.request.create.DogCreateRequestDTO;
import com.leverx.menagerie.mapper.DogMapper;
import com.leverx.menagerie.repository.DogRepository;
import com.leverx.menagerie.service.DogService;
import com.leverx.menagerie.service.PetService;
import com.sap.cds.services.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public DogsPetsView findDogById(Integer id) {
        Optional<DogsPetsView> optionalDogById = dogRepository.findById(id);

        return optionalDogById.orElseThrow(() -> new ServiceException(NOT_FOUND,
                String.format("Dog by id=%d does not exist", id)));
    }

    @Override
    public DogsPetsView createDog(DogCreateRequestDTO dogDTO) {
        Pets persistedNewPet = petService.createPet(dogDTO);
        Dogs newDog = dogMapper.toEntity(dogDTO);
        Dogs persistedNewDog = dogRepository.save(newDog);

        return dogMapper.toDogsPetsView(persistedNewPet, persistedNewDog);
    }

    @Override
    public List<DogsPetsView> createDog(List<DogCreateRequestDTO> dogDTOList) {
        List<Pets> persistedPetsList = petService.createPets(dogDTOList);

        List<Dogs> newDogsList = dogDTOList.stream()
                .map(dogMapper::toEntity)
                .collect(toList());

        List<Dogs> persistedDogsList = dogRepository.save(newDogsList);

        int newDogsSize = persistedPetsList.size();
        List<DogsPetsView> result = new ArrayList<>();

        for (int i = 0; i < newDogsSize; i++) {
            Pets currentPet = persistedPetsList.get(i);
            Dogs currentDog = persistedDogsList.get(i);
            DogsPetsView dogsPetsView = dogMapper.toDogsPetsView(currentPet, currentDog);

            result.add(dogsPetsView);
        }

        return result;
    }
}
