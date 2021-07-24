package com.leverx.menagerie.mapper;

import cds.gen.petservice.DogsPetsView;
import cds.gen.petservice.Pets;
import com.leverx.menagerie.dto.request.create.DogCreateRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE
)
public abstract class PetMapper {

    @Mapping(source = "owner_ID", target = "ownerId")
    public abstract Pets toEntity(DogCreateRequestDTO createRequest);

    public Pets toEntity(DogsPetsView dog) {
        if (dog == null) {
            return null;
        }

        Pets pet = createFactory();
        pet.setId(dog.getPetId());
        pet.setName(dog.getName());
        pet.setAge(dog.getAge());
        pet.setIsAlive(dog.getIsAlive());
        pet.setGender(dog.getGender());
        pet.setAnimalKind(dog.getAnimalKind());
        pet.setOwnerId(dog.getOwnerId());

        return pet;
    }

    @ObjectFactory
    public Pets createFactory() {
        return Pets.create();
    }

}
