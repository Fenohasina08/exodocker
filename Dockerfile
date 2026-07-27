# --- Étape de build ---
FROM amazoncorretto:21-alpine AS build
WORKDIR /app

COPY gradlew gradlew.bat build.gradle settings.gradle ./
COPY gradle ./gradle
COPY .env ./.env
RUN chmod +x gradlew

COPY src ./src

RUN ./gradlew bootJar -x test --no-daemon

# --- Étape d'exécution ---
FROM amazoncorretto:21-alpine
WORKDIR /app

COPY --from=build /app/build/libs/*-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
