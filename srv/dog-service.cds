using {com.leverx.menagerie as datamodel} from '../db/schema';

service DogService {
    entity Dogs as projection on datamodel.Dogs;
}