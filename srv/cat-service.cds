using {com.leverx.menagerie as datamodel} from '../db/schema';

service CatService {
    entity Cats as projection on datamodel.Cats;
}