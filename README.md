# 📚 API de Livros

Uma API RESTful desenvolvida em curso spring boot expert da udemy **Java Spring Boot**, utilizando **PostgreSQL** como banco de dados e **Docker** para containerização.  
O projeto oferece operações completas de **CRUD** (Create, Read, Update, Delete) para gerenciamento de **Autores**, **Livros** e **Usuários**.

Esse repositorio representa os commits feitos durante o curso para consolidar o aprendizado e consultar futuramente o que foi apresentado durante o curso

---

## 🚀 Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3+**
  - Spring Web
  - Spring Data JPA
  - Spring Validation
- **PostgreSQL**
- **Docker / Docker Compose**
- **Lombok**
- **Maven**

---

## 🧱 Estrutura da API

| Recurso | Endpoint Base | Descrição |
|----------|----------------|------------|
| **Autor** | `/api/autores` | Gerencia os autores dos livros |
| **Livro** | `/api/livros` | Gerencia os livros cadastrados |
| **Usuário** | `/api/usuarios` | Gerencia os usuários do sistema |

### Exemplos de Rotas

#### 📘 Livros
| Método | Rota | Descrição |
|--------|------|-----------|
| `GET` | `/api/livros` | Lista todos os livros |
| `GET` | `/api/livros/{id}` | Retorna um livro específico |
| `POST` | `/api/livros` | Cria um novo livro |
| `PUT` | `/api/livros/{id}` | Atualiza um livro existente |
| `DELETE` | `/api/livros/{id}` | Remove um livro |

#### ✍️ Autores
Mesma estrutura de CRUD em `/api/autores`

#### 👤 Usuários
Mesma estrutura de CRUD em `/api/usuarios`

---

## 🐘 Configuração do Banco de Dados

A API utiliza o **PostgreSQL**, com credenciais definidas no arquivo `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/library
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
