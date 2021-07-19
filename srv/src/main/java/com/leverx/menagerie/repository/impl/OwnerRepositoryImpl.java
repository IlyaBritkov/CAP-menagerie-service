package com.leverx.menagerie.repository.impl;

import cds.gen.petservice.Owners;
import cds.gen.petservice.Owners_;
import com.leverx.menagerie.repository.OwnerRepository;
import com.sap.cds.ql.Select;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.services.persistence.PersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = @Autowired)

@Repository
public class OwnerRepositoryImpl implements OwnerRepository {

    private final PersistenceService db;

    @Override
    public Optional<Owners> findById(Integer id) {
        CqnSelect findByIdSelect = Select.from(Owners_.class)
                .where(owner -> owner.ID().eq(id));

        return db.run(findByIdSelect).first(Owners.class);
    }
}
