# orcamento-api
API para gerenciar orçamento mensal familiar.

## Sobre
API desenvolvida com o intuito de estudar a linguagem Java usando Spring Framework, e PostgreSQL como banco de dados relacional.

## Tecnologias utilizadas
- Java
- Spring Boot
- Spring Data JPA
- Spring Security com JWT
- Spring Web Mvc
- Docker & Docker compose
- PostgreSQL
- Springdoc OpenApi3
- JUnit
- Maven
- Flyway

## Principais Recursos
- Create, Read, Update, Delete um Usuário
- Create, Read, Update, Delete uma Despesa
- Create, Read, Update, Delete uma Receita
- Retorna um resumo do orçamento mensal do usuário

## Execução

Pré-requisitos: [Docker and Docker Compose](https://www.docker.com/)

Executar docker-compose.yml

```sh
mvn clean package
docker-compose up
```
