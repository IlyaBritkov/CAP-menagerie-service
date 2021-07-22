package com.leverx.menagerie.repository.impl;

import cds.gen.com.leverx.menagerie.Dogs;
import cds.gen.com.leverx.menagerie.Dogs_;
import com.leverx.menagerie.repository.DogRepository;
import com.sap.cds.ql.Insert;
import com.sap.cds.ql.Select;
import com.sap.cds.ql.cqn.CqnInsert;
import com.sap.cds.services.persistence.PersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = @Autowired)

@Repository
public class DogRepositoryImpl implements DogRepository {

    private final PersistenceService db;

    @Override
    public Optional<cds.gen.petservice.Dogs> findById(Integer id) {
        Select<cds.gen.petservice.Dogs_> selectById = Select.from(cds.gen.petservice.Dogs_.class)
                .where(dog -> dog.pet_ID().eq(id))
                .limit(1);

        return db.run(selectById).first(cds.gen.petservice.Dogs.class);
    }

    @Override
    public Dogs save(Dogs newDog) {
        CqnInsert insert = Insert.into(Dogs_.CDS_NAME).entry(newDog);

        return db.run(insert).first(Dogs.class).get(); // TODO: 7/20/2021
    }

    @Override
    public List<Dogs> save(List<Dogs> newDogsList) {
        CqnInsert insert = Insert.into(Dogs_.CDS_NAME).entries(newDogsList);

        return db.run(insert).listOf(Dogs.class);
    }
}
