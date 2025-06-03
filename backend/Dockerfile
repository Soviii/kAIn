FROM eclipse-temurin:17-jdk AS build
WORKDIR /app
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw package -DskipTests

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/backend-1.0.0.jar ./backend.jar
EXPOSE 8080
CMD ["java", "-jar", "backend.jar"]

