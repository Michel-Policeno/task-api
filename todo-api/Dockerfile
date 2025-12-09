FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Stage 2: Runtime (somente JRE)
FROM eclipse-temurin:21-jre
WORKDIR /app

ARG PORT=8080
ENV PORT=$PORT
ENV SERVER_PORT=$PORT
ENV JAVA_OPTS="-Xms256m -Xmx512m"

COPY --from=build /app/target/*.jar app.jar

EXPOSE $PORT

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]