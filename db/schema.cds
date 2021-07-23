namespace com.leverx.menagerie;

using {cuid} from '@sap/cds/common';

type Gender : String enum {
    male;
    female;
}

type AnimalKind : String enum {
    Cats;
    Dogs;
}

entity Pets : cuid {
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

entity Cats { // todo

    key pet         : Composition of Pets;
        isCastrated : Boolean
}

entity Dogs : cuid {
    @assert.unique pet : Composition of Pets;
    noseIsDry          : Boolean
}

entity Owners : cuid {
    name    : String(30) not null;
    age     : Integer @assert.range : [
        0,
        150
    ];
    isAlive : Boolean default true not null;
    pets    : Association to many Pets
                  on pets.owner = $self;
}
