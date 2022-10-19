FROM amazoncorretto:17.0.4

RUN mkdir /opt/form-flow-starter-app
COPY . /opt/form-flow-starter-app
WORKDIR /opt/form-flow-starter-app

ARG APTIBLE_ENV=/opt/form-flow-starter-app/.aptible.env
RUN set -a  && \
    if [ -e ${APTIBLE_ENV} ] ; then . ${APTIBLE_ENV} ; fi && \
    ./gradlew assemble && \
    cp /opt/form-flow-starter-app/build/libs/app.jar app.jar
    
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/form-flow-starter-app/app.jar"]
