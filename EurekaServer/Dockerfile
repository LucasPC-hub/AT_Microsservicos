# Use the Maven image with OpenJDK 17 to build the project
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Build the project and verify the JAR file is created
RUN mvn clean package -DskipTests && \
    if [ ! -f target/EurekaServer-0.0.1-SNAPSHOT.jar ]; then echo "JAR file not found!"; exit 1; fi

# Use the OpenJDK 17 image to run the application
FROM openjdk:17-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file
COPY --from=build /app/target/EurekaServer-0.0.1-SNAPSHOT.jar app.jar

# Expose the port
EXPOSE 8761

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]