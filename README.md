# 📝 ProjetoTask API

API REST para gerenciamento de tarefas, desenvolvida com Spring Boot.

---

## 🚀 Tecnologias

- Java 21
- Spring Boot 3.4
- Spring Data JPA
- MySQL
- MapStruct
- Swagger / OpenAPI
- JUnit 5 + Mockito

---

## ✅ Funcionalidades

- Criar, listar, atualizar e deletar tarefas
- Filtrar tarefas por status
- Paginação e ordenação nos resultados
- Documentação interativa via Swagger

---

## ⚙️ Como rodar

### Pré-requisitos
- Java 21
- Maven
- MySQL rodando localmente

### 1. Clone o repositório
git clone https://github.com/cansado12/projetoTask.git
cd projetoTask

### 2. Configure o banco de dados
Crie um banco MySQL chamado `task_db` e edite o arquivo
`src/main/resources/application.properties`:

spring.datasource.url=jdbc:mysql://localhost:3306/task_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

### 3. Rode o projeto
mvn spring-boot:run

---

## 🧪 Testes

mvn test

---

## 📖 Documentação da API

Com o projeto rodando, acesse:

http://localhost:8080/swagger-ui.html

---

## 📌 Endpoints

| Método | Endpoint              | Descrição                     |
|--------|-----------------------|-------------------------------|
| GET    | /task                 | Lista todas as tasks          |
| GET    | /task/{id}            | Busca task por ID             |
| GET    | /task/status/{status} | Filtra tasks por status       |
| POST   | /task                 | Cria uma nova task            |
| PUT    | /task/{id}            | Atualiza uma task             |
| DELETE | /task/{id}            | Deleta uma task               |
