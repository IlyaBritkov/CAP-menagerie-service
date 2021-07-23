using {com.leverx.menagerie as datamodel} from '../db/schema';

service PetService {

    @readonly
    entity Pets         as projection on datamodel.Pets;

    entity CatsPetsView as
        select from datamodel.Cats
        inner join datamodel.Pets
            on Cats.pet.ID = Pets.ID;

    entity DogsPetsView as select from datamodel.Dogs {
        Dogs.ID,
        Dogs.noseIsDry,
        Dogs.pet,
        Dogs.pet.name,
        Dogs.pet.age,
        Dogs.pet.gender,
        Dogs.pet.animalKind,
        Dogs.pet.isAlive,
        Dogs.pet.owner
    };

    entity Owners       as projection on datamodel.Owners;
    action exchangePets(firstPetId : String, secondPetId : String);
}
