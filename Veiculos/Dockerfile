# Use a imagem base do Maven para compilar o projeto
FROM maven:3.8.4-openjdk-17 AS build

# Define o diretório de trabalho para a fase de build
WORKDIR /app

# Copia os arquivos de configuração do Maven e o código-fonte para o contêiner
COPY pom.xml .
COPY src ./src

# Compila o projeto e gera o arquivo JAR
RUN mvn clean package -DskipTests

# Use a imagem base do OpenJDK 17 para rodar a aplicação
FROM openjdk:17-slim

# Define o diretório de trabalho para a fase de runtime
WORKDIR /app

# Copia o arquivo JAR gerado na fase de build para o contêiner
COPY --from=build /app/target/Veiculos-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta que a aplicação irá rodar
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]