FROM ubuntu:latest
LABEL authors="Sezgin"

ENTRYPOINT ["top", "-b"]
# Use an OpenJDK 17 (Alpine-based) base image
FROM eclipse-temurin:17-jdk-alpine

# ARG for the JAR file name (optional, for flexibility)
ARG JAR_FILE=target/*.jar

# Copy the JAR to the container
COPY ${JAR_FILE} app.jar

# Expose the port your service runs on (OPTIONAL, for clarity)
# e.g., Eureka: 8761, Gateway: 8080, Product: 8081, Order: 8082
EXPOSE 8761

# Run the Spring Boot application
ENTRYPOINT ["java","-jar","/app.jar"]
