FROM amazoncorretto:17.0.4

RUN mkdir /opt/form-flow-starter-app
COPY . /opt/form-flow-starter-app
WORKDIR /opt/form-flow-starter-app

#ARG APTIBLE_ENV=/opt/form-flow-starter-app/.aptible.env
RUN set -a  && \
    if [ -e /opt/form-flow-starter-app/.aptible.env ] ; then echo "Load env 🍹" && . /opt/form-flow-starter-app/.aptible.env ; else echo "No env 🙅🏻‍♂️" ; fi && \
    if [ -z ${USERNAME+x} ]; then echo "username unset 👻" ; else echo "username set ✅" ; fi && \
    ./gradlew assemble && \
    ls -latr build/libs && \
    ls -latr /opt/form-flow-starter-app/build/libs && \
    cp /opt/form-flow-starter-app/build/libs/app.jar app.jar
    
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/form-flow-starter-app/app.jar"]
