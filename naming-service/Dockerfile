# First Stage: Extract layers from the layered JAR using Layertools
FROM eclipse-temurin:17.0.4.1_1-jre AS layertools

WORKDIR /app

# Copy the pre-built JAR file from the local machine
COPY target/*.jar app.jar

# Extract layers using Layertools
RUN java -Djarmode=layertools -jar app.jar extract

# Second Stage: Create a minimal runtime image using extracted layers
FROM eclipse-temurin:17.0.4.1_1-jre

WORKDIR /app

# Copy layers from the layertools stage into separate Docker layers

# Dependencies layer
COPY --from=layertools /app/*/ ./

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]


