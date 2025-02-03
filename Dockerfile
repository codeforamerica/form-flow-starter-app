FROM eclipse-temurin:21-jdk-alpine

# Install LibreOffice (which includes soffice)
RUN apk add --no-cache libreoffice libreoffice-common

RUN mkdir /opt/form-flow-starter-app /opt/pdf-fonts
COPY . /opt/form-flow-starter-app
COPY src/main/resources/pdf-fonts/* /opt/pdf-fonts/
WORKDIR /opt/form-flow-starter-app

RUN ./gradlew assemble && \
    cp /opt/form-flow-starter-app/build/libs/app.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/form-flow-starter-app/app.jar"]
