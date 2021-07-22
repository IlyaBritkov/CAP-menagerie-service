-- owners
INSERT INTO com_leverx_menagerie_Owners(ID, name, age, isAlive) VALUES (1,'Ilya',19,true);
INSERT INTO com_leverx_menagerie_Owners(ID, name, age, isAlive) VALUES (2,'VladiSlave',19,true);

-- pets
INSERT INTO com_leverx_menagerie_Pets(ID, name, age, isAlive, gender, animalKind, owner_ID) VALUES (1,'TikoDog',1,true,'male','Dogs',1);

-- cats & dogs
INSERT INTO com_leverx_menagerie_Dogs(pet_ID, noseIsDry) VALUES (1,true);