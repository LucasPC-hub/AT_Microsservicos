# Use the Maven image with OpenJDK 17 to build the project
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Build the project
RUN mvn clean package -DskipTests

# Use the OpenJDK 17 image to run the application
FROM openjdk:17-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file
COPY --from=build /app/target/eureka-server-0.0.1-SNAPSHOT.jar app.jar

# Expose the port
EXPOSE 8761

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]