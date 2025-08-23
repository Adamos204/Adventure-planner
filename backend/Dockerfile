# ---------- BUILD STAGE ----------
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom and download deps first (cache-friendly)
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# Copy source and build
COPY src ./src
RUN mvn -q -DskipTests package
RUN cp target/*-SNAPSHOT.jar app.jar || cp target/*.jar app.jar

# ---------- RUNTIME STAGE ----------
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/app.jar app.jar

EXPOSE 8080
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75"
ENV SPRING_PROFILES_ACTIVE=docker
ENTRYPOINT ["java","-jar","/app/app.jar"]
