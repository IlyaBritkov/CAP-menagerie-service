package com.leverx.menagerie.service.impl;

import cds.gen.petservice.Owners;
import cds.gen.petservice.Pets;
import com.leverx.menagerie.dto.request.create.DogCreateRequestDTO;
import com.leverx.menagerie.mapper.PetMapper;
import com.leverx.menagerie.repository.PetRepository;
import com.leverx.menagerie.service.OwnerService;
import com.leverx.menagerie.service.PetService;
import com.sap.cds.services.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Qualifier("ownerServiceImpl")
    private final OwnerService ownerService;

    private final PetMapper petMapper;

    @Override
    public Pets findEntityById(Integer id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new ServiceException(NOT_FOUND,
                        String.format("Pet by id=%d does not exist", id)));
    }

    @Override
    public cds.gen.com.leverx.menagerie.Pets createPet(DogCreateRequestDTO dogDTO) {
        cds.gen.com.leverx.menagerie.Pets newPet = petMapper.toEntity(dogDTO);
        return petRepository.save(newPet);
    }

    @Override
    public List<cds.gen.com.leverx.menagerie.Pets> createPets(List<DogCreateRequestDTO> dogDTOList) {
        List<cds.gen.com.leverx.menagerie.Pets> newPets = dogDTOList.stream()
                .map(petMapper::toEntity)
                .collect(toList());
        return petRepository.save(newPets);
    }

    @Override
    @Transactional
    public void exchangePets(Integer firstPetId, Integer secondPetId) {
        // check are pet ids different
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


        Integer firstOwnerId = firstPet.getOwnerId();
        Integer secondOwnerId = secondPet.getOwnerId();

        // exchange
        firstPet.setOwnerId(secondOwnerId);
        secondPet.setOwnerId(firstOwnerId);

        petRepository.update(asList(firstPet, secondPet));
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

        Integer firstOwnerId = firstPet.getOwnerId();
        Integer secondOwnerId = secondPet.getOwnerId();

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
}
