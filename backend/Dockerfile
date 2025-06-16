FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Copy Maven settings and dependencies first for caching 
    # - need to copy, even though redundant because of bind mount (bind mounts are active at runtime, not build time)
# COPY pom.xml ./pom.xml
# COPY .mvn .mvn
# COPY src ./src
# COPY ./.m2 ./.m2

COPY . .
# Expose default Spring Boot port
EXPOSE 8080

# Run using Maven wrapper
CMD ["./mvnw", "spring-boot:run"]
# CMD ["tail", "-f", "/dev/null"] # use if want to have backend container  without spring boot running
