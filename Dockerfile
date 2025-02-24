# Use OpenJDK 17 base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Spring Boot JAR file from the build stage (target folder)
COPY target/*.jar app.jar

# Expose the port your Spring Boot app runs on (default: 8080)
EXPOSE 7070

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
