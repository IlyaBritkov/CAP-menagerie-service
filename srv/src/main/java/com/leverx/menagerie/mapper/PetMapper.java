package com.leverx.menagerie.mapper;

import cds.gen.petservice.DogsPetsView;
import cds.gen.petservice.CatsPetsView;
import cds.gen.petservice.Pets;
import com.leverx.menagerie.dto.request.create.PetCreateRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE
)
public abstract class PetMapper {

    @Mapping(source = "owner_ID", target = "ownerId")
    public abstract Pets toEntity(PetCreateRequestDTO createRequest);

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

    public Pets toEntity(CatsPetsView cat) {
        if (cat == null) {
            return null;
        }

        Pets pet = createFactory();
        pet.setId(cat.getPetId());
        pet.setName(cat.getName());
        pet.setAge(cat.getAge());
        pet.setIsAlive(cat.getIsAlive());
        pet.setGender(cat.getGender());
        pet.setAnimalKind(cat.getAnimalKind());
        pet.setOwnerId(cat.getOwnerId());

        return pet;
    }

    @ObjectFactory
    public Pets createFactory() {
        return Pets.create();
    }

}
