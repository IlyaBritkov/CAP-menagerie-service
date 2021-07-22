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
    public Optional<Pets> findById(Integer id) {
        CqnSelect findByIdSelect = Select.from(Pets_.class)
                .where(p -> p.ID().eq(id));

        return db.run(findByIdSelect).first(Pets.class);
    }

    @Override
    public cds.gen.com.leverx.menagerie.Pets save(cds.gen.com.leverx.menagerie.Pets newPet) {
        CqnInsert insert = Insert.into(cds.gen.com.leverx.menagerie.Pets_.CDS_NAME).entry(newPet);

        return db.run(insert).first(cds.gen.com.leverx.menagerie.Pets.class).get(); // TODO: 7/20/2021
    }

    @Override
    public List<cds.gen.com.leverx.menagerie.Pets> save(List<cds.gen.com.leverx.menagerie.Pets> newPetsList) {
        CqnInsert insert = Insert.into(cds.gen.com.leverx.menagerie.Pets_.CDS_NAME).entries(newPetsList);

        return db.run(insert).listOf(cds.gen.com.leverx.menagerie.Pets.class); // TODO: 7/20/2021
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
