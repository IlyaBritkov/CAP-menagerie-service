package com.leverx.menagerie.mapper;

import cds.gen.com.leverx.menagerie.Dogs;
import com.leverx.menagerie.dto.request.create.DogCreateRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE
)
public abstract class DogMapper {

    @Mapping(source = "ID", target = "petId")
    public abstract Dogs toEntity(DogCreateRequestDTO createRequest);

    @ObjectFactory
    public Dogs createFactory() {
        return Dogs.create();
    }
}
