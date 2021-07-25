package com.leverx.menagerie.mapper;

import cds.gen.com.leverx.menagerie.Cats;
import cds.gen.petservice.CatsPetsView;
import cds.gen.petservice.Pets;
import com.leverx.menagerie.dto.request.create.CatCreateRequestDTO;
import com.leverx.menagerie.dto.request.update.CatUpdateRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE
)
public abstract class CatMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "pet.id", target = "petId")
    public abstract Cats toEntity(CatCreateRequestDTO createRequest, Pets pet);

    public Cats toEntity(CatsPetsView catsPetsView) {
        if (catsPetsView == null) {
            return null;
        }

        Cats cat = catCreateFactory();

        cat.setId(catsPetsView.getId());
        cat.setPetId(catsPetsView.getPetId());
        cat.setIsCastrated(catsPetsView.getIsCastrated());

        return cat;
    }

    @Mapping(source = "pet.id", target = "petId")
    @Mapping(source = "cat.id", target = "id")
    public abstract CatsPetsView toCatsPetsView(Pets pet, Cats cat);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "petId", ignore = true)
    public abstract void updateCat(CatUpdateRequestDTO catDTO, @MappingTarget CatsPetsView persistedCat);

    @ObjectFactory
    public Cats catCreateFactory() {
        return Cats.create();
    }

    @ObjectFactory
    public CatsPetsView catsPetsViewCreateFactory() {
        return CatsPetsView.create();
    }
}
