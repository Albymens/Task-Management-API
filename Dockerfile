FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

COPY mvnw* pom.xml ./
COPY .mvn .mvn

RUN ./mvnw dependency:go-offline

COPY src src

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar task-management-0.0.1-SNAPSHOT.jar

EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "task-management-0.0.1-SNAPSHOT.jar"]
