package com.leverx.menagerie.repository.impl;

import cds.gen.petservice.Pets;
import cds.gen.petservice.Pets_;
import com.leverx.menagerie.repository.PetRepository;
import com.sap.cds.ql.Insert;
import com.sap.cds.ql.Select;
import com.sap.cds.ql.Update;
import com.sap.cds.ql.cqn.CqnInsert;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.ql.cqn.CqnUpdate;
import com.sap.cds.services.persistence.PersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = @Autowired)

@Repository
public class PetRepositoryImpl implements PetRepository {

    private final PersistenceService db;

    @Override
    public Optional<Pets> findById(String id) {
        CqnSelect findByIdSelect = Select.from(Pets_.class)
                .where(p -> p.ID().eq(id));

        return db.run(findByIdSelect)
                .first(Pets.class);
    }

    @Override
    public Pets save(Pets newPet) {
        CqnInsert insert = Insert.into(Pets_.CDS_NAME)
                .entry(newPet);

        return db.run(insert)
                .single(Pets.class);
    }

    @Override
    public List<Pets> save(List<Pets> newPetsList) {
        CqnInsert insert = Insert.into(Pets_.CDS_NAME)
                .entries(newPetsList);

        return db.run(insert)
                .listOf(Pets.class);
    }

    @Override
    public Pets update(Pets updatedPet) {
        CqnUpdate update = Update.entity(Pets_.CDS_NAME)
                .data(updatedPet);

        return db.run(update)
                .single(Pets.class);
    }

    @Override
    public List<Pets> update(List<Pets> updatedPets) {
        CqnUpdate update = Update.entity(Pets_.class)
                .entries(updatedPets);

        return db.run(update)
                .listOf(Pets.class);
    }

    @Override
    public List<Pets> findAllByOwnerId(String ownerId) {
        CqnSelect select = Select.from(Pets_.class)
                .where(p -> p.owner_ID().eq(ownerId));

        return db.run(select)
                .listOf(Pets.class);
    }

}
