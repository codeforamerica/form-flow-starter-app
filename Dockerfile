#FROM openjdk:17.0
FROM amazoncorretto:17.0.4
COPY . .
ADD . /app
RUN set -a && . /app/.aptible.env && ./gradlew assemble
RUN cp build/libs/*SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/app.jar","--spring.profiles.active=demo,actuator"]
