FROM ubuntu:latest AS build

RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven

COPY . .

RUN mvn clean install

FROM openjdk:17-jdk-slim
WORKDIR /app

# Copiar o arquivo JAR gerado para a imagem final
COPY --from=build target/CorraAgil-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8080
# Comando para rodar o aplicativo
ENTRYPOINT ["java", "-jar", "/app.jar"]

