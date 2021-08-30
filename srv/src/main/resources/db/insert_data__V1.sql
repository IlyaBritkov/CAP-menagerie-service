-- owners
INSERT INTO com_leverx_menagerie_Owners(ID, name, age, isAlive) VALUES ('286f38f2-7c3f-4f71-8f25-896f4296d1c7','Ilya',19,true);
INSERT INTO com_leverx_menagerie_Owners(ID, name, age, isAlive) VALUES ('c127838c-62d4-4b90-b641-751f988b8afd','VladiSlave',20,false);

-- pets
INSERT INTO com_leverx_menagerie_Pets(ID, name, age, isAlive, gender, animalKind, owner_ID) VALUES ('da3d95c7-c858-4260-b350-d26d1c459aba','TikoDog',1,true,'male','Dogs','c127838c-62d4-4b90-b641-751f988b8afd');

-- cats & dogs
INSERT INTO com_leverx_menagerie_Dogs(ID,pet_ID, noseIsDry) VALUES ('d792e943-c149-4959-b7b9-db890a68f661','da3d95c7-c858-4260-b350-d26d1c459aba',true);