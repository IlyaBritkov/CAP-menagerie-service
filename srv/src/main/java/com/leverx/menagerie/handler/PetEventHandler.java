package com.leverx.menagerie.handler;

import cds.gen.petservice.CatsPetsView;
import cds.gen.petservice.CatsPetsView_;
import cds.gen.petservice.DogsPetsView;
import cds.gen.petservice.DogsPetsView_;
import cds.gen.petservice.ExchangePetsContext;
import cds.gen.petservice.PetService_;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leverx.menagerie.dto.request.create.CatCreateRequestDTO;
import com.leverx.menagerie.dto.request.create.DogCreateRequestDTO;
import com.leverx.menagerie.dto.request.update.CatUpdateRequestDTO;
import com.leverx.menagerie.dto.request.update.DogUpdateRequestDTO;
import com.leverx.menagerie.service.CatService;
import com.leverx.menagerie.service.DogService;
import com.leverx.menagerie.service.PetService;
import com.sap.cds.services.ServiceException;
import com.sap.cds.services.cds.CdsCreateEventContext;
import com.sap.cds.services.cds.CdsUpdateEventContext;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.sap.cds.services.ErrorStatuses.BAD_REQUEST;
import static com.sap.cds.services.cds.CdsService.EVENT_CREATE;
import static com.sap.cds.services.cds.CdsService.EVENT_UPDATE;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j

@Component
@ServiceName(PetService_.CDS_NAME)
public class PetEventHandler implements EventHandler { // TODO: 7/25/2021 check how deletion of owner affects on pets

    @Qualifier("petServiceImpl")
    private final PetService petService;

    private final DogService dogService;

    private final CatService catService;

    private final ObjectMapper objectMapper;

    @On(event = EVENT_CREATE, entity = DogsPetsView_.CDS_NAME)
    public void onDogCreate(CdsCreateEventContext context) {
        List<DogCreateRequestDTO> requestDogDTOsList = getRequestDTOListFromCreateContext(context, DogCreateRequestDTO.class);

        List<DogsPetsView> dogsPetsViewList = dogService.createDog(requestDogDTOsList);
        List<Map<String, Object>> resultList = convertObjectListToObjectMapList(dogsPetsViewList);

        context.setResult(resultList); // TODO: 7/25/2021 maybe add context.completed()
    }

    @On(event = EVENT_CREATE, entity = CatsPetsView_.CDS_NAME)
    public void onCatCreate(CdsCreateEventContext context) {
        List<CatCreateRequestDTO> requestCatDTOsList = getRequestDTOListFromCreateContext(context, CatCreateRequestDTO.class);

        List<CatsPetsView> catsPetsViewList = catService.createCat(requestCatDTOsList);
        List<Map<String, Object>> resultList = convertObjectListToObjectMapList(catsPetsViewList);

        context.setResult(resultList);
    }

    @On(event = EVENT_UPDATE, entity = DogsPetsView_.CDS_NAME)
    public void onDogUpdate(CdsUpdateEventContext context) {
        DogUpdateRequestDTO requestDogDTO = getRequestDTOFromUpdateContext(context, DogUpdateRequestDTO.class);

        DogsPetsView updatedDog = dogService.updateDog(requestDogDTO);

        List<Map<String, Object>> resultList = convertObjectToObjectMapList(updatedDog);

        context.setResult(resultList);
        context.setCompleted();
    }

    @On(event = EVENT_UPDATE, entity = CatsPetsView_.CDS_NAME)
    public void onCatUpdate(CdsUpdateEventContext context) {
        CatUpdateRequestDTO requestCatDTO = getRequestDTOFromUpdateContext(context, CatUpdateRequestDTO.class);

        CatsPetsView updatedCat = catService.updateCat(requestCatDTO);

        List<Map<String, Object>> resultList = convertObjectToObjectMapList(updatedCat);

        context.setResult(resultList);
        context.setCompleted();
    }

    /**
     * Exchange pets, if they belong different owners
     *
     * @param context - action context
     */
    @On(event = "exchangePets")
    public void exchangePets(ExchangePetsContext context) {
        String firstPetId = context.getFirstPetId();
        String secondPetId = context.getSecondPetId();

        log.info("Exchange request with pet ids: [{},{}]", firstPetId, secondPetId);

        petService.exchangePets(firstPetId, secondPetId);
        context.setCompleted();
    }

    private <T> T getRequestDTOFromUpdateContext(CdsUpdateEventContext context, Class<T> objectClass) {
        List<Map<String, Object>> entriesMaps = context.getCqn().asUpdate().entries();

        List<T> objectList = entriesMaps.stream()
                .map(entryMap -> objectMapper.convertValue(entryMap, objectClass))
                .collect(toList());

        if (objectList.size() == 1 && objectList.get(0) != null) {
            return objectList.get(0);
        } else {
            throw new ServiceException(BAD_REQUEST, "Unsupported request body compound");
        }
    }

    private <T> List<T> getRequestDTOListFromCreateContext(CdsCreateEventContext context, Class<T> objectClass) {
        List<Map<String, Object>> entriesMaps = context.getCqn().asInsert().entries();

        return entriesMaps.stream()
                .map(entryMap -> objectMapper.convertValue(entryMap, objectClass))
                .collect(toList());
    }

    private <T> List<Map<String, Object>> convertObjectToObjectMapList(T object) {
        return convertObjectListToObjectMapList(singletonList(object));
    }

    @SuppressWarnings("unchecked")
    private <T> List<Map<String, Object>> convertObjectListToObjectMapList(List<T> objectList) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        for (T item : objectList) {
            Map<String, Object> mappedItem = objectMapper.convertValue(item, Map.class);
            resultList.add(mappedItem);
        }

        return resultList;
    }

}
