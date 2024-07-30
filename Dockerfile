FROM eclipse-temurin:17-jre
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/new_keystore.p12 new_keystore.p12
COPY src/main/resources/application.properties application.properties
ENTRYPOINT ["java", "-jar", "/app.jar"]
