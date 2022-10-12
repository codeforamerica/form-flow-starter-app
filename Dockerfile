FROM amazoncorretto:17-alpine-jdk
COPY . .
RUN set -a && . /app/.aptible.env && ./gradlew assemble
RUN cp build/libs/*SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/app.jar","--spring.profiles.active=demo"]
