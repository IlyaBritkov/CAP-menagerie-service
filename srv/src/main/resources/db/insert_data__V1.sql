-- owners
INSERT INTO com_leverx_menagerie_Owners(ID, name, age, isAlive) VALUES (1,'Ilya',19,true);
INSERT INTO com_leverx_menagerie_Owners(ID, name, age, isAlive) VALUES (2,'VladiSlave',19,true);

-- pets
INSERT INTO com_leverx_menagerie_Pets(ID, name, age, isAlive, gender, animalKind, owner_ID) VALUES (1,'TikoDog',1,true,'male','Dogs',1);
INSERT INTO com_leverx_menagerie_Pets(ID, name, age, isAlive, gender, animalKind, owner_ID) VALUES (2,'TestCat',3,true,'female','Cats',2);

-- cats & dogs
INSERT INTO com_leverx_menagerie_Dogs(pet_ID, noseIsDry) VALUES (1,true);
INSERT INTO com_leverx_menagerie_Cats(pet_ID, isCastrated) VALUES (2,false);