# teste-java

Sistema de cadastro de Produtos e Clientes com autenticação JWT — implementação em Java/Spring Boot.

## 🚀 Quick Start

```bash
git clone git@github.com:anderson-sart/teste-java.git
cd teste-java
docker compose up -d
```

Acesse: http://localhost:8080
**Login**: admin | **Senha**: admin123

## Tecnologias

- **Java 21** + **Spring Boot 3.3**
- **Spring Security** — autenticação stateless via JWT (jjwt 0.12)
- **Spring Data JPA** + **Hibernate** — ORM
- **PostgreSQL 16** — banco de dados
- **Flyway** — migrations versionadas
- **Thymeleaf** — template engine (server-side rendering)
- **Alpine.js** — reatividade no frontend
- **Bootstrap 5** — UI
- **Lombok** — redução de boilerplate
- **Docker + Docker Compose** — infraestrutura

## Estrutura

```
src/main/java/com/softline/api/
├── config/
│   ├── SecurityConfig.java        # Spring Security + JWT stateless
│   └── GlobalExceptionHandler.java
├── controller/
│   ├── WebController.java         # Rotas web (Thymeleaf)
│   ├── AuthController.java        # POST /api/login
│   ├── ProdutoController.java     # CRUD /api/produtos
│   ├── ClienteController.java     # CRUD /api/clientes
│   └── StatsController.java       # GET /api/stats
├── dto/                           # Records Java 21
├── model/                         # Entidades JPA + soft delete
├── repository/                    # Spring Data JPA + busca unaccent
├── security/
│   ├── JwtService.java
│   └── JwtFilter.java
└── service/                       # Lógica de negócio

src/main/resources/
├── db/migration/
│   ├── V1__create_tables.sql
│   ├── V2__seed_admin.sql
│   └── V3__seed_data.sql          # 50 produtos + 50 clientes
└── templates/                     # Views Thymeleaf
    ├── login.html
    ├── menu.html
    ├── produtos/
    └── clientes/
```

## Páginas

| Rota | Descrição |
|---|---|
| `/` | Redireciona para login ou menu |
| `/login` | Autenticação |
| `/menu` | Dashboard |
| `/produtos` | Listagem com busca, paginação e ordenação |
| `/produtos/create` | Cadastro de produto |
| `/produtos/edit/{id}` | Edição de produto |
| `/clientes` | Listagem com busca, paginação e ordenação |
| `/clientes/create` | Cadastro de cliente |
| `/clientes/edit/{id}` | Edição de cliente |

## API REST

Todas as rotas `/api/**` (exceto `/api/login`) exigem header:
```
Authorization: Bearer <token>
```

| Método | Rota | Descrição |
|---|---|---|
| POST | `/api/login` | Autenticar |
| GET | `/api/produtos` | Listar produtos |
| POST | `/api/produtos` | Criar produto |
| PUT | `/api/produtos/{id}` | Atualizar produto |
| DELETE | `/api/produtos/{id}` | Excluir produto (soft delete) |
| GET | `/api/clientes` | Listar clientes |
| POST | `/api/clientes` | Criar cliente |
| PUT | `/api/clientes/{id}` | Atualizar cliente |
| DELETE | `/api/clientes/{id}` | Excluir cliente (soft delete) |
| GET | `/api/stats` | Totais do dashboard |

### Parâmetros de listagem

```
GET /api/produtos?search=mouse&page=1&perPage=10&sortBy=codigo&sortDir=DESC
```

### Campos — Produtos

| Campo | Tipo | Restrição |
|---|---|---|
| descricao | String | obrigatório, max 60 |
| codigoBarras | String | opcional, max 14 |
| valorVenda | BigDecimal | obrigatório, ≥ 0 |
| pesoBruto | BigDecimal | obrigatório, ≥ 0 |
| pesoLiquido | BigDecimal | obrigatório, ≥ 0 |

### Campos — Clientes

| Campo | Tipo | Restrição |
|---|---|---|
| nome | String | obrigatório, max 60 |
| fantasia | String | opcional, max 100 |
| documento | String | obrigatório, max 18 (CPF/CNPJ) |
| endereco | String | opcional |

## Recursos

- Paginação (10 registros por página)
- Busca em tempo real com `unaccent` (ignora acentos)
- Ordenação por colunas (ASC/DESC)
- Soft delete (`deleted_at`)
- 50 produtos + 50 clientes de exemplo
- Token JWT injetado server-side (httpOnly cookie)

## Autor

Anderson Sartori
