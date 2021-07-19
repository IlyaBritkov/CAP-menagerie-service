package com.leverx.menagerie.repository.impl;

import cds.gen.petservice.Pets;
import cds.gen.petservice.Pets_;
import com.leverx.menagerie.repository.PetRepository;
import com.sap.cds.ql.Select;
import com.sap.cds.ql.Update;
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
    public Optional<Pets> findById(Integer id) {
        CqnSelect findByIdSelect = Select.from(Pets_.class)
                .where(p -> p.ID().eq(id));

        return db.run(findByIdSelect).first(Pets.class);
    }

    @Override
    public void update(Pets updatedPet) {
        CqnUpdate update = Update.entity(Pets_.class).data(updatedPet);
        db.run(update);
    }

    @Override
    public void update(List<Pets> updatedPets) {
        CqnUpdate update = Update.entity(Pets_.class).entries(updatedPets);
        db.run(update);
    }
}
