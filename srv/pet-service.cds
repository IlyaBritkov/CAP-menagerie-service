using {com.leverx.menagerie as datamodel} from '../db/schema';

service PetService {
    @readonly
    entity Pets as projection on datamodel.Pets;

    entity DogsPetsView as select from datamodel.Dogs inner join datamodel.Pets on Dogs.pet.ID=Pets.ID; 

    entity CatsPetsView as select from datamodel.Cats inner join datamodel.Pets on Cats.pet.ID=Pets.ID; 
    
    entity Owners as projection on datamodel.Owners;

    action exchangePets(firstPetId : Integer, secondPetId : Integer);
}
