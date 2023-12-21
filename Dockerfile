FROM eclipse-temurin:17-jdk-alpine

RUN mkdir /opt/form-flow-starter-app /opt/pdf-fonts
COPY . /opt/form-flow-starter-app
COPY src/main/resources/pdf-fonts/* /opt/pdf-fonts/
WORKDIR /opt/form-flow-starter-app

ARG APTIBLE_ENV=/opt/form-flow-starter-app/.aptible.env
RUN set -a  && \
    apk add --update npm dart-sass && \
    if [ -e ${APTIBLE_ENV} ] ; then . ${APTIBLE_ENV} ; fi && \
    ./gradlew assemble && \
    cp /opt/form-flow-starter-app/build/libs/app.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/form-flow-starter-app/app.jar"]
