#сборка
FROM maven:3.9.6-eclipse-temurin-17 AS builder
#путь к рабочей директории
WORKDIR /app
#путь к файлу, который нужно скопировать  - куда скопировать
COPY mvnw pom.xml ./
COPY ./src ./src
RUN mvn clean install -DskipTests

#запуск
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
EXPOSE 8080
COPY --from=builder /app/target/*.jar /app/*.jar
ENTRYPOINT ["java", "-jar", "/app/*.jar"]
