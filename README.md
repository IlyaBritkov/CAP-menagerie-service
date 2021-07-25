<h1 align="center"><b>CAP-menagerie-service web app</b></h1>

<h2 align="center">Predefined requirements</h2>
Before starting the project you have to do
or ensure that the following are done: <br>

1. [Install Node.js](https://nodejs.org/)
  → always use the latest LTS version. <br>
2. [Install SQLite](https://sqlite.org/download.html) (only required on Windows) <br>
3. Install *@sap/cds-dk* globally with: <br> 
   `npm i -g @sap/cds-dk` <br>
   `cds # test it`
4. [Install JDK 8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html) 
   and set up your environment. <br>
5. [Install Maven](https://maven.apache.org/download.cgi)
   and set up your environment. <br>
   
For more information see [CAP documentation](https://cap.cloud.sap/docs/about/).

---

<h2 align="center">Project sources</h2>

Project was initialized with the following Maven archetype:

```shell
mvn -B archetype:generate -DarchetypeArtifactId=cds-services-archetype -DarchetypeGroupId=com.sap.cds -DarchetypeVersion=RELEASE -DgroupId=com.sap.cap -DartifactId=menagerie-service
```
<br>

See the [Postman API collection](https://www.getpostman.com/collections/520471b778b0f77a415c).

---
<h2 align="center">Getting start</h2>

In order to **generate all required for development source files** based on .cds-files,
execute the following command:
```shell
mvn clean install
```

Mark directory `cds` under `srv/src/gen/java` directory
as **Generated Sources Root** in IntelliJ IDEA.

Locally the app uses embedded  SQLite database.<br>
Execute the following commands in the *root directory* of the project.<br>
In order to **install SQLite to the project**
execute the following command:
```shell
npm install --save-dev sqlite3
```

In order to **initialize the database with the defined domain model**,
execute the following command:
```shell
cds deploy --to sqlite
```
<br>

Now you're able to develop project or launch it.

In order to **compile & launch** application execute the following command:
```shell
mvn clean spring-boot:run
```
