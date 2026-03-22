# Stage 1 - build
FROM eclipse-temurin:25-jdk AS build
WORKDIR /workspace
COPY pom.xml pom.xml
COPY .mvn/ .mvn/
COPY mvnw mvnw
RUN sh mvnw dependency:resolve
COPY src/ src/
RUN sh mvnw package -DskipTests

# Stage 2 - runtime
FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /workspace/target/*.jar pantrypal.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "pantrypal.jar"]
