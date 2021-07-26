package com.leverx.menagerie.service.impl;

import cds.gen.petservice.CatsPetsView;
import cds.gen.petservice.DogsPetsView;
import cds.gen.petservice.Owners;
import cds.gen.petservice.Pets;
import com.leverx.menagerie.dto.request.create.PetCreateRequestDTO;
import com.leverx.menagerie.mapper.PetMapper;
import com.leverx.menagerie.repository.PetRepository;
import com.leverx.menagerie.service.OwnerService;
import com.leverx.menagerie.service.PetService;
import com.sap.cds.services.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.sap.cds.services.ErrorStatuses.BAD_REQUEST;
import static com.sap.cds.services.ErrorStatuses.NOT_FOUND;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    private final CheckExistenceServiceImpl checkExistenceService;

    private final OwnerService ownerService;

    private final PetMapper petMapper;

    @Override
    public Pets findEntityById(String id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new ServiceException(NOT_FOUND,
                        String.format("Pet by id=%s does not exist", id)));
    }

    @Override
    public Pets createPet(PetCreateRequestDTO petDTO) {
        Pets newPet = petMapper.toEntity(petDTO);
        validateOwnerExistence(newPet);

        return petRepository.save(newPet);
    }

    @Override
    public List<Pets> createPets(List<? extends PetCreateRequestDTO> petDTOList) {
        List<Pets> newPets = petDTOList.stream()
                .map(petMapper::toEntity)
                .collect(toList());

        newPets.forEach(this::validateOwnerExistence);

        return petRepository.save(newPets);
    }

    @Override
    @Transactional
    public void exchangePets(String firstPetId, String secondPetId) {
        // check if pet ids different
        if (Objects.equals(firstPetId, secondPetId)) {
            throw new ServiceException(BAD_REQUEST, "To exchange pets there are should be different pets");
        }

        Pets firstPet = findEntityById(firstPetId);
        Pets secondPet = findEntityById(secondPetId);

        log.debug("First pet by id={}:{}", firstPetId, firstPet);
        log.debug("Second pet by id={}:{}", secondPet, secondPet);

        // validation of pets and owners
        validatePetsAreAlive(firstPet, secondPet);
        validatePetOwnersForPetExchange(firstPet, secondPet);


        String firstOwnerId = firstPet.getOwnerId();
        String secondOwnerId = secondPet.getOwnerId();

        // exchange
        firstPet.setOwnerId(secondOwnerId);
        secondPet.setOwnerId(firstOwnerId);

        petRepository.update(asList(firstPet, secondPet));
    }

    @Override
    public Pets updatePet(DogsPetsView dogPetView) {
        Pets newPet = petMapper.toEntity(dogPetView);

        return petRepository.update(newPet);
    }

    @Override
    public Pets updatePet(CatsPetsView catPetView) {
        Pets newPet = petMapper.toEntity(catPetView);

        return petRepository.update(newPet);
    }

    @Override
    public long deleteOwnerById(String ownerId) {
        long amountOfDeletedOwners = ownerService.deleteById(ownerId);

        removeOwnerFromPets(ownerId);

        return amountOfDeletedOwners;
    }

    @Override
    public void removeOwnerFromPets(String ownerId) {
        List<Pets> pets = petRepository.findAllByOwnerId(ownerId);

        if (!pets.isEmpty()) {
            pets.forEach(pet -> {
                pet.setOwnerId(null);
                pet.setOwner(null);
            });

            petRepository.update(pets);
        }
    }

    private void validatePetsAreAlive(Pets firstPet, Pets secondPet) {
        if (!firstPet.getIsAlive() || !secondPet.getIsAlive()) {
            throw new ServiceException(BAD_REQUEST, "To exchange pets they should be alive");
        }
    }

    /**
     * Performs all needed validation of owners for pet exchange
     **/
    private void validatePetOwnersForPetExchange(Pets firstPet, Pets secondPet) {
        validatePetOwnersDifference(firstPet, secondPet);

        String firstOwnerId = firstPet.getOwnerId();
        String secondOwnerId = secondPet.getOwnerId();

        Owners firstOwner = ownerService.findEntityById(firstOwnerId);
        Owners secondOwner = ownerService.findEntityById(secondOwnerId);

        validatePetOwnersAreAlive(firstOwner, secondOwner);
    }

    private void validatePetOwnersDifference(Pets firstPet, Pets secondPet) {
        boolean isValid = !Objects.equals(firstPet.getOwnerId(), secondPet.getOwnerId());
        if (!isValid) {
            throw new ServiceException(BAD_REQUEST, "To exchange pets there are should be two different owners");
        }
    }

    private void validatePetOwnersAreAlive(Owners firstOwner, Owners secondOwner) {
        if (!firstOwner.getIsAlive() || !secondOwner.getIsAlive()) {
            throw new ServiceException(BAD_REQUEST, "To exchange pets their owners should be alive");
        }
    }

    private void validateOwnerExistence(Pets newPet) {
        String ownerId = newPet.getOwnerId();
        boolean isOwnerByIdExists = checkExistenceService.isOwnerByIdExists(ownerId);
        if (!isOwnerByIdExists) {
            throw new ServiceException(BAD_REQUEST, String.format("Owner with id = %s doesn't exist", ownerId));
        }
    }

}
