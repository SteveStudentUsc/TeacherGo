# Etapa 1: Compilar el proyecto con Gradle y JDK 21
FROM gradle:8.5-jdk21 as build
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test

# Etapa 2: Imagen ligera para ejecutar el JAR
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
