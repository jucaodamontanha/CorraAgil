# Projeto Corrida Ágil

Este projeto é uma aplicação Spring Boot para gerenciar cadastros e corridas. Ele inclui funcionalidades de autenticação, envio de emails de confirmação e reset de senha, além de operações CRUD para cadastros e gerenciamento de corridas.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.0
- Spring Data JPA
- PostgreSQL
- Jakarta Validation
- Spring Mail

## Configuração do Ambiente

### Banco de Dados

Certifique-se de que o PostgreSQL esteja instalado e em execução. Configure as propriedades do banco de dados no arquivo `application.properties`:

```properties
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.username=admin
spring.datasource.password=5YZpWlndkTJQOlVtnKBnbMfIwOZ2fAQg
spring.datasource.url=jdbc:postgresql://dpg-cu2q428gph6c73biop3g-a/corraagil_wuor
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
Servidor de Email
Configure as propriedades do servidor de email no arquivo application.properties:

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=jucaodamontanha@gmail.com
spring.mail.password=dptk rpqc fsjd vokr
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```
## Executando a Aplicação

### Clone o repositório:

`git clone https://github.com/Equipe-Bronze/CorraAgil.git`

### Navegue até o diretório do projeto:

cd corrida-agil

`Compile e execute a aplicação:`

`./mvnw spring-boot:run`

Endpoints da API
## Cadastro
. GET /cadastro/todos - Lista todos os cadastros

. GET /cadastro/{id} - Busca um cadastro pelo ID

. POST /cadastro - Cria um novo cadastro

.PUT /cadastro/{id} - Atualiza um cadastro existente

.DELETE /cadastro/{id} - Deleta um cadastro pelo ID

.POST /cadastro/login - Realiza login de um usuário

. PUT /cadastro/{id}/reset - Atualiza a senha de um cadastro

## Reset de Senha

.POST /resetSenha - Envia um token de reset de senha para o email do usuário

.POST /verificaToken - Verifica a validade de um token de reset de senha

.POST /saveSenha - Salva a nova senha do usuário

## Corrida

. POST /corrida/iniciar - Inicia uma nova corrida

. POST /corrida/pausar/{id} - Pausa uma corrida existente

. POST /corrida/continuar/{id} - Continua uma corrida pausada

. POST /corrida/finalizar/{id} - Finaliza uma corrida existente

## Contribuindo

Faça um fork do projeto

Crie uma branch para sua feature (git checkout -b feature/nova-feature)

Commit suas mudanças (git commit -am 'Adiciona nova feature')

Faça o push para a branch (git push origin feature/nova-feature)

Crie um novo Pull Request

### Changelog

Todas as mudanças importantes neste projeto serão documentadas neste arquivo.

[Versão 1.0.0] - 2025-01-29

## Adicionado
.Funcionalidade de cadastro de usuários

.Funcionalidade de autenticação de usuários

.Funcionalidade de reset de senha

.Funcionalidade de gerenciamento de corridas (iniciar, pausar, continuar, finalizar)

## Licença

Este projeto está licenciado sob a Licença MIT. Veja o arquivo LICENSE para mais detalhes.