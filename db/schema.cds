namespace com.leverx.menagerie;

type Gender : String enum {
    male;
    female;
}

type AnimalKind : String enum {
    Cats;
    Dogs;
}

entity Pets {
    key ID         : Integer not null;
        name       : String(30) not null;
        age        : Integer @assert.range : [
            0,
            200
        ];
        isAlive    : Boolean default true not null;
        gender     : Gender;
        animalKind : AnimalKind;
        owner      : Association to Owners;
}

entity Cats {
    key pet         : Composition of Pets;
        isCastrated : Boolean
}

entity Dogs {
        key pet       : Composition of Pets;
        noseIsDry : Boolean
}

entity Owners {
        // todo replace ID by uuid
    key ID      : Integer not null;
        name    : String(30) not null;
        age     : Integer @assert.range : [
            0,
            150
        ];
        isAlive : Boolean default true not null;
        pets    : Association to many Pets
                      on pets.owner = $self;
}
