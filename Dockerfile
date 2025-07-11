FROM ubuntu:latest AS build

# Atualizar e instalar dependências em um único comando RUN
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Definir o diretório de trabalho
WORKDIR /app

# Copiar todos os arquivos para o diretório de trabalho
COPY . .

# Construir o projeto Maven
RUN mvn clean install

# Usar uma imagem mais leve para a fase de execução
FROM openjdk:17-jdk-slim

# Definir o diretório de trabalho
WORKDIR /app

# Expor a porta 8080
EXPOSE 8080

# Copiar o jar gerado da fase de build
COPY --from=build /app/target/CorraAgil-0.0.1-SNAPSHOT.jar app.jar

# Definir o ponto de entrada
ENTRYPOINT ["java", "-jar", "app.jar"]