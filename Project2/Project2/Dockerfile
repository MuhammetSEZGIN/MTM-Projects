FROM ubuntu:latest
LABEL authors="Sezgin"

ENTRYPOINT ["top", "-b"]

FROM eclipse-temurin:17-jdk-alpine

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]
