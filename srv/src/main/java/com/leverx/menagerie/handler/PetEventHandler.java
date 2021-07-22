package com.leverx.menagerie.handler;

import cds.gen.com.leverx.menagerie.Dogs;
import cds.gen.petservice.ExchangePetsContext;
import cds.gen.petservice.PetService_;
import com.fasterxml.jackson.core.type.TypeReference;
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
import java.util.HashMap;
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

    @On(event = EVENT_CREATE, entity = cds.gen.petservice.Dogs_.CDS_NAME)
    public void onDogCreate(CdsCreateEventContext context) {
        System.out.println("In onDogCreate method"); // TODO: 7/21/2021

        List<DogCreateRequestDTO> requestDogDTOsList = getRequestDTOsFromContext(context, DogCreateRequestDTO.class);

        List<Dogs> result = dogService.createDog(requestDogDTOsList);

//        context.setResult(context.getCqn().asInsert().entries()); // TODO: 7/21/2021

        Integer petId = result.get(0).getPetId();

        Dogs dogTest = result.get(0);

        Map<String, Object> map = objectMapper.convertValue(dogTest, new TypeReference<Map<String, Object>>() {
        });

//        List<Map<String, Object>> collect = result.stream()
//                .map(dog -> objectMapper.convertValue(dog, Map.class))
//                .collect(toList());
//        collect.forEach(System.out::println);

        Map<String, Object> testMap = new HashMap<>();
        testMap.put("isAlive", true);
        testMap.put("gender", "male");
        testMap.put("owner_ID", 1);
        testMap.put("name", "TestDog");
        testMap.put("animalKind", "Dogs");
        testMap.put("ID", 2);
        testMap.put("age", 5);

        testMap.put("pet_ID", dogTest.getPetId());
        testMap.put("noseIdDry", dogTest.getNoseIsDry());
        System.out.println(testMap);

        cds.gen.petservice.Dogs dogById = dogService.findDogById(2);// // TODO: 7/21/2021
        Map<String,Object> map1 = objectMapper.convertValue(dogById, Map.class);
        List<Map<String, Object>> testMapList = new ArrayList<>();
        testMapList.add(map1);
        System.out.println(testMapList);

//        List<Map> resultMapList = result.stream()
//                .map(pet -> objectMapper.convertValue(pet, Map.class))
//                .collect(toList());

        List<Map<String, Object>> entries = context.getCqn().asInsert().entries();
        System.out.println(entries);
        context.setResult(testMapList);
    }


//    @On(event = EVENT_CREATE, entity = Dogs_.CDS_NAME)
//    public void onDogCreate(CdsCreateEventContext context) {
//
////        context.getCqn().entries().forEach(e -> products.put(e.get("ID"), e));
////        context.setResult(context.getCqn().entries());
//    }

    /**
     * Exchange pets, if they belong different owners
     *
     * @param context
     */
    @On(event = "exchangePets")
    public void exchangePets(ExchangePetsContext context) {
        Integer firstPetId = context.getFirstPetId();
        Integer secondPetId = context.getSecondPetId();

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

}
