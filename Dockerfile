# ─────────────────────────────────────────────
# Stage 1: Build con Maven
# ─────────────────────────────────────────────
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copiar pom.xml primero para aprovechar el cache de Maven
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente y compilar
COPY src ./src
RUN mvn clean package -DskipTests -B

# ─────────────────────────────────────────────
# Stage 2: Runtime liviano
# ─────────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine AS runner

WORKDIR /app

# Usuario no-root por seguridad
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

# Copiar el JAR generado
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]