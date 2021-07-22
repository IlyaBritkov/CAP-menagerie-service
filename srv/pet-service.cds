using {com.leverx.menagerie as datamodel} from '../db/schema';

service PetService {
    @readonly
    entity Pets as projection on datamodel.Pets;

    // entity Dogs as projection on datamodel.Dogs;

    entity Dogs as select from datamodel.Dogs inner join datamodel.Pets on Dogs.pet_ID=Pets.ID; 

    entity Cats as projection on datamodel.Cats;
    
    entity Owners as projection on datamodel.Owners excluding {pets};

    action exchangePets(firstPetId : Integer, secondPetId : Integer);
}
