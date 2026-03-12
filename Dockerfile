# Use lightweight Java runtime
FROM eclipse-temurin:17-jdk-alpine

# Create app directory
WORKDIR /app

# Copy jar file
COPY target/*.jar app.jar

# Default profile
ENV SPRING_PROFILES_ACTIVE=prod

# Expose port
EXPOSE 8000

# Run application
ENTRYPOINT ["java","-jar","app.jar"]
