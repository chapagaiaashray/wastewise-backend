# === Stage 1: Build with Maven ===
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source files
COPY src ./src

# Build the project
RUN mvn clean package -DskipTests

# === Stage 2: Run the built app ===
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy the built jar from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
