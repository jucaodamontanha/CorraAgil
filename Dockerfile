FROM ubuntu:latest AS build

RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven

COPY . .

RUN mvn clean install

FROM openjdk:17-jdk-slim
EXPOSE 8080

# Copiar o arquivo JAR gerado para a imagem final
COPY --from=build /Users/lineker/Documents/Projetos/CorraAgil/target/CorraAgil-0.0.1-SNAPSHOT.jar /app.jar

# Comando para rodar o aplicativo
ENTRYPOINT ["java", "-jar", "/app.jar"]

