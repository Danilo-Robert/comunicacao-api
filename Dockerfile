FROM maven:3.8-openjdk-17 AS BUILD
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTest

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar  /app/comunicacao_api.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/comunicacao_api.jar"]