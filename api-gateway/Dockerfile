FROM eclipse-temurin:17.0.4.1_1-jre as builder
WORKDIR extracted
ADD target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]