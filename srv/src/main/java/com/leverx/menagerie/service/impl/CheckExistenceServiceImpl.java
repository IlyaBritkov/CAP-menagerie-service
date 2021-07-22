package com.leverx.menagerie.service.impl;

import com.leverx.menagerie.service.CheckExistenceService;
import com.leverx.menagerie.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(onConstructor_ = @Autowired)

@Service
public class CheckExistenceServiceImpl implements CheckExistenceService {

    private final OwnerService ownerService;

    @Override
    public boolean isOwnerByIdExists(Integer id) {
        return ownerService.isOwnerByIdExists(id);
    }

}
