FROM eclipse-temurin:17-jre
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/springboot.p12 springboot.p12
COPY src/main/resources/application.properties application.properties
ENTRYPOINT ["java", "-jar", "/app.jar"]
