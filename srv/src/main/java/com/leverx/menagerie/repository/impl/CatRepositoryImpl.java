package com.leverx.menagerie.repository.impl;

import cds.gen.com.leverx.menagerie.Cats;
import cds.gen.com.leverx.menagerie.Cats_;
import cds.gen.petservice.CatsPetsView;
import cds.gen.petservice.CatsPetsView_;
import com.leverx.menagerie.repository.CatRepository;
import com.sap.cds.ql.Insert;
import com.sap.cds.ql.Select;
import com.sap.cds.ql.Update;
import com.sap.cds.ql.cqn.CqnInsert;
import com.sap.cds.services.persistence.PersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = @Autowired)

@Repository
public class CatRepositoryImpl implements CatRepository {

    private final PersistenceService db;

    @Override
    public Optional<CatsPetsView> findCatPetViewById(String id) {
        Select<CatsPetsView_> selectById = Select.from(CatsPetsView_.class)
                .where(cat -> cat.ID().eq(id))
                .limit(1);

        return db.run(selectById)
                .first(CatsPetsView.class); // TODO: 7/25/2021 maybe replace by .single();
    }

    @Override
    public Cats save(Cats newCat) {
        CqnInsert insertNewCat = Insert.into(Cats_.CDS_NAME)
                .entry(newCat);

        return db.run(insertNewCat)
                .single(Cats.class);
    }

    @Override
    public List<Cats> save(List<Cats> newCatsList) {
        CqnInsert insert = Insert.into(Cats_.CDS_NAME)
                .entries(newCatsList);

        return db.run(insert)
                .listOf(Cats.class);
    }

    @Override
    public CatsPetsView update(CatsPetsView newCat) { // TODO: 7/25/2021 try or get rid of it.
        Update<CatsPetsView_> updateCat = Update.entity(CatsPetsView_.class)
                .data(newCat);

        return db.run(updateCat)
                .single(CatsPetsView.class);
    }

    @Override
    public Cats update(Cats cat) { // TODO: 7/25/2021 get rid of it if this is unnecessary.
        Update<Cats_> updateCat = Update.entity(Cats_.class)
                .data(cat);

        return db.run(updateCat)
                .single(Cats.class);
    }

}
