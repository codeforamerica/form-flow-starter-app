FROM amazoncorretto:17.0.4

RUN mkdir /opt/form-flow-starter-app
COPY . /opt/form-flow-starter-app
WORKDIR /opt/form-flow-starter-app

ARG APTIBLE_ENV=/app/.aptible.env
RUN if [ -e $APTIBLE_ENV ] ; then set -a && . $APTIBLE_ENV ; fi
RUN ./gradlew assemble

RUN cp build/libs/*SNAPSHOT.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/form-flow-starter-app/app.jar"]
