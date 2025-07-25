# 🚗 Desafio - Cadastro de Veículos

Este projeto é um sistema de cadastro de veículos que integra com a [API da Tabela FIPE](https://veiculos.fipe.org.br/), salva as informações em banco de dados e publica eventos via RabbitMQ.

## 📦 Tecnologias utilizadas

- **Kotlin + Spring Boot 3**
- **Spring Data JPA**
- **Spring Cloud OpenFeign** – integração com API externa (FIPE)
- **RabbitMQ** – publicação de eventos
- **PostgreSQL** – banco de dados relacional
- **Flyway** – versionamento e migração do banco
- **Docker / Docker Compose** – containers de banco e mensageria
- **JUnit 5 + MockK** – testes unitários
- **OpenAPI (Swagger)** – documentação da API REST

## ⚙️ Como rodar o projeto localmente

### 1. Clone o repositório

### 2. Suba os containers

Certifique-se de que você tem o **Docker** e o **Docker Compose** instalados. Depois, suba o ambiente com:

```bash
docker-compose up -d
```

Isso irá subir:

- PostgreSQL na porta `5432`
- RabbitMQ (painel: http://localhost:15672 | user: `guest` / pass: `guest`)

### 3. Rode a aplicação

Você pode rodar pela IDE (IntelliJ ou VSCode com suporte a Kotlin) ou via terminal:

```bash
./gradlew bootRun
```

A aplicação estará disponível em:

```
http://localhost:8080
```

### 4. Documentação da API

Acesse a documentação gerada com Swagger:

```
http://localhost:8080/swagger-ui.html
```

## 🔄 Endpoints principais

- `POST /veiculos` – Cadastra um novo veículo (Deve ter o id da marca, do modelo e do ano)
exemplo de body: 
`{
  "placa": "ABC1D23f",
  "precoAnuncio": 45900.00,
  "ano": 2006,
  "idMarca": 6,
  "idModelo": 59
}`


- `GET /veiculos/{placa}` – Busca um veículo por placa
- `GET /veiculos/marca/{id}` – Lista paginada de veículos por marca

## 🧪 Testes

Para rodar os testes unitários:

```bash
./gradlew test
```

> Os testes estão na pasta `src/test/java/com/dryve/desafio/service`

## 🐘 Flyway & Migrations

As migrations estão no diretório:

```
src/main/resources/db/migration
```

Elas são aplicadas automaticamente ao iniciar a aplicação.

## 📁 Estrutura de pacotes

```
com.dryve.desafio
├── client            # Cliente Feign para consumir API FIPE
├── controller        # Endpoints REST
├── domain            # Entidades JPA
├── dto               # DTOs de entrada e saída
├── event             # Eventos e publishers
├── repository        # Interfaces JPA
├── service           # Regras de negócio
├── config            # Configurações
├── resources
│   └── db/migration  # Scripts Flyway
└── test              # Testes automatizados
```

## 🧠 Regras de negócio

- A **placa** deve ser única no banco de dados.
- O **preço FIPE** é buscado em tempo real na API externa.
- Caso a API da FIPE não retorne o valor, o cadastro é recusado.
- Ele verifica se já existe a marca e modelo recuperados cadastrados no banco, se nao tiver ele cadastra antes de cadastrar o veiculo,
se tiver registro já ele só referencia no novo veiculo.
- Um evento `VeiculoCadastradoEvent` é publicado via RabbitMQ após o cadastro.
- O evento publicado aparece no log.

---
