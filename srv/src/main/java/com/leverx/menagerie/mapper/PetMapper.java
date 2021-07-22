package com.leverx.menagerie.mapper;

import cds.gen.com.leverx.menagerie.Pets;
import com.leverx.menagerie.dto.request.create.DogCreateRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE
)
public abstract class PetMapper {

    @Mapping(source = "ID", target = "id")
    @Mapping(source = "owner_ID", target = "ownerId")
    public abstract Pets toEntity(DogCreateRequestDTO createRequest);

    @ObjectFactory
    public Pets createFactory() {
        return Pets.create();
    }
}
