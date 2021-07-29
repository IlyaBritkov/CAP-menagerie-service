FROM adoptopenjdk/openjdk8:ubi

COPY srv/target/menagerie-service-exec.jar animalsservice/menagerie-service-exec.jar
COPY  sqlite.db animalsservice/sqlite.db
COPY animalsrun.sh /scripts/animalsrun.sh

ENTRYPOINT ["/scripts/animalsrun.sh"]

EXPOSE 8080