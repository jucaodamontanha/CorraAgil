
# ====== Stage de build ======
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app
COPY pom.xml ./
RUN mvn -B -q dependency:go-offline
COPY . .
RUN mvn -B -q clean package -DskipTests

# ====== Stage de runtime (Ubuntu Jammy) ======
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app
COPY --from=build /app/target/CorraAgil-0.0.1-SNAPSHOT.jar app.jar

# Se precisar de certificados/tempo de execução extra:
# RUN apt-get update && apt-get install -y --no-install-recommends ca-certificates && \
#     rm -rf /var/lib/apt/lists/*

ENV JAVA_OPTS=""
ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["bash", "-lc", "java $JAVA_OPTS -jar app.jar"]
