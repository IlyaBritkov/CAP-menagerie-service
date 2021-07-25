package com.leverx.menagerie.service;

import cds.gen.petservice.CatsPetsView;
import com.leverx.menagerie.dto.request.create.CatCreateRequestDTO;
import com.leverx.menagerie.dto.request.update.CatUpdateRequestDTO;

import java.util.List;

public interface CatService {

    CatsPetsView findCatPetViewById(String id);

    CatsPetsView createCat(CatCreateRequestDTO catDTO);

    List<CatsPetsView> createCat(List<CatCreateRequestDTO> catDTOList);

    CatsPetsView updateCat(CatUpdateRequestDTO requestCatDTO);
}
