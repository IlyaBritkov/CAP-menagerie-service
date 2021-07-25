package com.leverx.menagerie.service.impl;

import cds.gen.com.leverx.menagerie.Cats;
import cds.gen.petservice.CatsPetsView;
import cds.gen.petservice.Pets;
import com.codepoetics.protonpack.StreamUtils;
import com.leverx.menagerie.dto.request.create.CatCreateRequestDTO;
import com.leverx.menagerie.dto.request.update.CatUpdateRequestDTO;
import com.leverx.menagerie.mapper.CatMapper;
import com.leverx.menagerie.repository.CatRepository;
import com.leverx.menagerie.service.CatService;
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
public class CatServiceImpl implements CatService {

    private final CatRepository catRepository;

    private final PetService petService;

    private final CatMapper catMapper;

    @Override
    public CatsPetsView findCatPetViewById(String id) {
        Optional<CatsPetsView> optionalCatById = catRepository.findCatPetViewById(id);

        return optionalCatById.orElseThrow(() -> new ServiceException(NOT_FOUND,
                String.format("Cat by id=%s does not exist", id)));
    }

    @Override
    public CatsPetsView createCat(CatCreateRequestDTO catDTO) {
        Pets persistedNewPet = petService.createPet(catDTO);
        Cats newCat = catMapper.toEntity(catDTO, persistedNewPet);
        Cats persistedNewCat = catRepository.save(newCat);

        return catMapper.toCatsPetsView(persistedNewPet, persistedNewCat);
    }

    @Override
    public List<CatsPetsView> createCat(List<CatCreateRequestDTO> catDTOList) {
        List<Pets> persistedPetsList = petService.createPets(catDTOList);

        List<Cats> newCatsList = StreamUtils
                .zipWithIndex(catDTOList.stream())
                .map(item -> {
                    int index = (int) item.getIndex();
                    CatCreateRequestDTO currentCat = item.getValue();
                    Pets currentPet = persistedPetsList.get(index);

                    return catMapper.toEntity(currentCat, currentPet);
                }).collect(toList());


        List<Cats> persistedCatsList = catRepository.save(newCatsList);

        return StreamUtils
                .zipWithIndex(persistedCatsList.stream())
                .map(item -> {
                    int index = (int) item.getIndex();
                    Cats currentCat = item.getValue();
                    Pets currentPet = persistedPetsList.get(index);

                    return catMapper.toCatsPetsView(currentPet, currentCat);
                }).collect(toList());
    }

    @Override
    public CatsPetsView updateCat(CatUpdateRequestDTO requestCatDTO) {
        final String catId = requestCatDTO.getId();
        CatsPetsView persistedCatPetView = findCatPetViewById(catId);

        catMapper.updateCat(requestCatDTO, persistedCatPetView);

        Pets updatedPet = petService.updatePet(persistedCatPetView);
        Cats newCat = catMapper.toEntity(persistedCatPetView);
        Cats updatedDog = catRepository.update(newCat);

        return catMapper.toCatsPetsView(updatedPet, updatedDog);
    }
}
