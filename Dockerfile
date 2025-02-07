FROM openjdk:17-jdk-slim AS build

RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/*.jar task-management-0.0.1-SNAPSHOT.jar

EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "task-management-0.0.1-SNAPSHOT.jar"]
