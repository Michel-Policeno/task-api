FROM maven:3.9.5-openjdk-21 AS build
WORKDIR /app

COPY pom.xml .
COPY src /app/src

# Compila o projeto e gera o JAR. O comando -DskipTests agiliza o processo.
RUN mvn clean package -DskipTests

# Usa uma imagem minimalista que contém apenas o JRE para rodar o JAR,
FROM openjdk:21-jre-slim-bullseye
WORKDIR /app

ARG PORT=8080
ENV SERVER_PORT $PORT
COPY --from=build /app/target/*.jar app.jar
EXPOSE $PORT

# Define o comando de entrada para iniciar a aplicação JAR
ENTRYPOINT ["java", "-jar", "app.jar"]