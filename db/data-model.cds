namespace com.leverx.menagerie;

entity Cats {
     key ID      : Integer not null;
         name    : String(30) not null;
         age     : Integer;
         isAlive : Boolean default true;
         owner   : Association to Owners;
}

entity Dogs {
     key ID      : Integer not null;
         name    : String(30) not null;
         age     : Integer;
         isAlive : Boolean default true;
         owner   : Association to Owners;
}

entity Owners {
     key ID      : Integer not null;
         name    : String(30) not null;
         age     : Integer;
         isAlive : Boolean default true;
}
