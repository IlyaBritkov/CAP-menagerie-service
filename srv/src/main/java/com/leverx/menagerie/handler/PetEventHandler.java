package com.leverx.menagerie.handler;

import cds.gen.petservice.DogsPetsView;
import cds.gen.petservice.DogsPetsView_;
import cds.gen.petservice.ExchangePetsContext;
import cds.gen.petservice.PetService_;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leverx.menagerie.dto.request.create.DogCreateRequestDTO;
import com.leverx.menagerie.service.DogService;
import com.leverx.menagerie.service.PetService;
import com.sap.cds.services.cds.CdsCreateEventContext;
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

import static com.sap.cds.services.cds.CdsService.EVENT_CREATE;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j

@Component
@ServiceName(PetService_.CDS_NAME)
public class PetEventHandler implements EventHandler {

    @Qualifier("petServiceImpl")
    private final PetService petService;

    private final DogService dogService;

    private final ObjectMapper objectMapper;

    @On(event = EVENT_CREATE, entity = DogsPetsView_.CDS_NAME)
    public void onDogCreate(CdsCreateEventContext context) {

        List<DogCreateRequestDTO> requestDogDTOsList = getRequestDTOsFromContext(context, DogCreateRequestDTO.class);

        List<DogsPetsView> dogsPetsViewList = dogService.createDog(requestDogDTOsList);
        List<Map<String, Object>> resultList = convertObjectListToObjectMapList(dogsPetsViewList);

        context.setResult(resultList);
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

    private <T> List<T> getRequestDTOsFromContext(CdsCreateEventContext context, Class<T> objectClass) {
        List<Map<String, Object>> entriesMaps = context.getCqn().asInsert().entries();

        return entriesMaps.stream()
                .map(entryMap -> objectMapper.convertValue(entryMap, objectClass))
                .collect(toList());
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
