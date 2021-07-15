using {com.leverx.menagerie as datamodel} from '../db/schema';

service OwnerService {
    entity Owners as projection on datamodel.Owners;
}
