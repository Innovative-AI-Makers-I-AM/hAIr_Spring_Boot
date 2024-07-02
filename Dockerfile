FROM openjdk:17-alpine
RUN apk update
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/springboot.p12 springboot.p12
COPY src/main/resources/application.properties application.properties
ENTRYPOINT ["java", "-jar", "/app.jar"]