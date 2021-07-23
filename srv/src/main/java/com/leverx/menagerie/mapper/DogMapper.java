package com.leverx.menagerie.mapper;

import cds.gen.com.leverx.menagerie.Dogs;
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
public abstract class DogMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "pet.id", target = "petId")
    public abstract Dogs toEntity(DogCreateRequestDTO createRequest, Pets pet);

    @Mapping(source = "pet.id", target = "petId")
    @Mapping(source = "dog.id", target = "id")
    public abstract DogsPetsView toDogsPetsView(Pets pet, Dogs dog);

    @ObjectFactory
    public Dogs dogCreateFactory() {
        return Dogs.create();
    }

    @ObjectFactory
    public DogsPetsView dogsPetsViewCreateFactory() {
        return DogsPetsView.create();
    }

}
