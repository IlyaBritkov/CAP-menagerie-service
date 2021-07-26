package com.leverx.menagerie.service.impl;

import cds.gen.petservice.Owners;
import com.leverx.menagerie.repository.OwnerRepository;
import com.leverx.menagerie.service.OwnerService;
import com.sap.cds.services.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.sap.cds.services.ErrorStatuses.NOT_FOUND;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j

@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;

    @Override
    public Owners findEntityById(String id) {
        return ownerRepository.findById(id)
                .orElseThrow(() -> new ServiceException(NOT_FOUND,
                        String.format("Owner by id=%s does not exist", id)));
    }

    @Override
    public boolean isOwnerByIdExists(String id) {
        return ownerRepository.isOwnerByIdExists(id);
    }

    @Override
    public long deleteById(String ownerId) {
        long deletedRows = ownerRepository.deleteById(ownerId);

        if (deletedRows == 0) {
            throw new ServiceException(NOT_FOUND, String.format("Owner with ID = %s doesn't exist", ownerId));
        }

        return deletedRows;
    }

}
