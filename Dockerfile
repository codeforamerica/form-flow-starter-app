FROM amazoncorretto:17-alpine-jdk
COPY . .

#RUN test $user && echo ?
#RUN test $key && echo ?

RUN ./gradlew assemble
RUN cp build/libs/*SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/app.jar","--spring.profiles.active=demo"]
