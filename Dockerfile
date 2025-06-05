# Use the official OpenJDK image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the backend project
COPY . .

# Build the app
RUN ./mvnw clean install

# Start the Spring Boot app
CMD ["./mvnw", "spring-boot:run"]
