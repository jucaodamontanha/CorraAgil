
# ====== Stage de build ======
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copia os POMs primeiro para aproveitar cache de dependências
COPY pom.xml ./
# Se tiver módulos, copie os poms dos módulos também:
# COPY module-a/pom.xml module-a/pom.xml
# COPY module-b/pom.xml module-b/pom.xml

# Baixa dependências sem rodar testes (mais rápido)
RUN mvn -B -q dependency:go-offline

# Agora copia o restante do código
COPY . .

# Build do JAR (sem testes; ative se precisar)
RUN mvn -B -q clean package -DskipTests

# ====== Stage de runtime (leve) ======
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copia o JAR gerado (ajuste o nome se necessário)
COPY --from=build /app/target/CorraAgil-0.0.1-SNAPSHOT.jar app.jar

# Variáveis úteis
ENV JAVA_OPTS=""
ENV PORT=8080

# Exponha a porta (opcional — Render usa PORT)
EXPOSE 8080

# EntryPoint com suporte a JAVA_OPTS
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
