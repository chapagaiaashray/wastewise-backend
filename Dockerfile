# Start from an official JDK image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy Maven wrapper and project files
COPY . .

# Give execution permission to Maven wrapper
RUN chmod +x ./mvnw

# Build the application
RUN ./mvnw clean install

# Expose port
EXPOSE 8080

# Run the Spring Boot application
CMD ["./mvnw", "spring-boot:run"]
