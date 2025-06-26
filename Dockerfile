FROM openjdk:17-jdk-alpine
LABEL maintainer="mmasenheimer"
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]