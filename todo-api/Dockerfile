FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src /app/src

# Compila o projeto e gera o JAR. O comando -DskipTests agiliza o processo.
RUN mvn clean package -DskipTests

# Usa uma imagem minimalista que contém apenas o JRE para rodar o JAR,
FROM openjdk:17-jre-slim
WORKDIR /app

ARG PORT=8080
ENV SERVER_PORT $PORT
COPY --from=build /app/target/*.jar app.jar
EXPOSE $PORT

# Define o comando de entrada para iniciar a aplicação JAR
ENTRYPOINT ["java", "-jar", "app.jar"]