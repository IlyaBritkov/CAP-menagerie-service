package com.leverx.menagerie.mapper;

import cds.gen.com.leverx.menagerie.Dogs;
import cds.gen.petservice.DogsPetsView;
import cds.gen.petservice.Pets;
import com.leverx.menagerie.dto.request.create.DogCreateRequestDTO;
import com.leverx.menagerie.dto.request.update.DogUpdateRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE
)
public abstract class DogMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "pet.id", target = "petId")
    public abstract Dogs toEntity(DogCreateRequestDTO createRequest, Pets pet);

    public Dogs toEntity(DogsPetsView dogsPetsView){
        if ( dogsPetsView == null ) {
            return null;
        }

        Dogs dog = dogCreateFactory();

        dog.setId(dogsPetsView.getId());
        dog.setPetId(dogsPetsView.getPetId());
        dog.setNoseIsDry(dogsPetsView.getNoseIsDry());

        return dog;
    }

    @Mapping(source = "pet.id", target = "petId")
    @Mapping(source = "dog.id", target = "id")
    public abstract DogsPetsView toDogsPetsView(Pets pet, Dogs dog);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "petId", ignore = true)
    public abstract void updateDog(DogUpdateRequestDTO dogDTO, @MappingTarget DogsPetsView persistedDog);

    @ObjectFactory
    public Dogs dogCreateFactory() {
        return Dogs.create();
    }

    @ObjectFactory
    public DogsPetsView dogsPetsViewCreateFactory() {
        return DogsPetsView.create();
    }
}
