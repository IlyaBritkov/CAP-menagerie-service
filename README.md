"# CAP-menagerie-service web app" 

See the Postman API collection [there](https://www.getpostman.com/collections/520471b778b0f77a415c).

In order to **compile & launch** application execute the following command:
```shell
mvn clean spring-boot:run
```


In order to **install SQLite to the project**
execute  the following command:
```shell
npm install --save-dev sqlite3
```

In order to **initialize the database with the defined domain model**,
execute the following command:
```shell
cds deploy --to sqlite
```
