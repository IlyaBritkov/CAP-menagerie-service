package com.leverx.menagerie.service.impl;

import cds.gen.com.leverx.menagerie.Dogs;
import cds.gen.petservice.DogsPetsView;
import cds.gen.petservice.Pets;
import com.codepoetics.protonpack.StreamUtils;
import com.leverx.menagerie.dto.request.create.DogCreateRequestDTO;
import com.leverx.menagerie.dto.request.update.DogUpdateRequestDTO;
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
    public DogsPetsView findDogPetViewById(String id) {
        Optional<DogsPetsView> optionalDogById = dogRepository.findDogPetViewById(id);

        return optionalDogById.orElseThrow(() -> new ServiceException(NOT_FOUND,
                String.format("Dog by id=%s does not exist", id)));
    }

    @Override
    public DogsPetsView createDog(DogCreateRequestDTO dogDTO) {
        Pets persistedNewPet = petService.createPet(dogDTO);
        Dogs newDog = dogMapper.toEntity(dogDTO, persistedNewPet);
        Dogs persistedNewDog = dogRepository.save(newDog);

        return dogMapper.toDogsPetsView(persistedNewPet, persistedNewDog);
    }

    @Override
    public List<DogsPetsView> createDog(List<DogCreateRequestDTO> dogDTOList) {
        List<Pets> persistedPetsList = petService.createPets(dogDTOList);

        List<Dogs> newDogsList = StreamUtils
                .zipWithIndex(dogDTOList.stream())
                .map(item -> {
                    int index = (int) item.getIndex();
                    DogCreateRequestDTO currentDog = item.getValue();
                    Pets currentPet = persistedPetsList.get(index);

                    return dogMapper.toEntity(currentDog, currentPet);
                }).collect(toList());


        List<Dogs> persistedDogsList = dogRepository.save(newDogsList);

        return StreamUtils
                .zipWithIndex(persistedDogsList.stream())
                .map(item -> {
                    int index = (int) item.getIndex();
                    Dogs currentDog = item.getValue();
                    Pets currentPet = persistedPetsList.get(index);

                    return dogMapper.toDogsPetsView(currentPet, currentDog);
                }).collect(toList());
    }

    @Override
    public DogsPetsView updateDog(DogUpdateRequestDTO requestDogDTO) {
        final String dogId = requestDogDTO.getId();
        DogsPetsView persistedDogPetView = findDogPetViewById(dogId);

        dogMapper.updateDog(requestDogDTO, persistedDogPetView);

        Pets updatedPet = petService.updatePet(persistedDogPetView);
        Dogs newDog = dogMapper.toEntity(persistedDogPetView);
        Dogs updatedDog = dogRepository.update(newDog);

        return dogMapper.toDogsPetsView(updatedPet, updatedDog);
    }

}
