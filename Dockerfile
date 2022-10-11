FROM openjdk:17.0
COPY . .
RUN ./gradlew assemble
RUN test $user && echo ?
RUN test $key && echo ?
RUN cp build/libs/*SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/app.jar","--spring.profiles.active=demo"]