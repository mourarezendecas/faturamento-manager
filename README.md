# API - Faturamento Manager

API REST desenvolvida com Spring Boot para gerenciamento de clientes, pedidos, itens e cartões de fidelidade.

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Lombok
- Maven

---

## Documentações

- Diagrama Entidade-Relacionamento (DER)
- Documentação da API (Swagger/OpenAPI) *(caso implemente futuramente)*

---

# Como executar a aplicação

## 1. Criar o banco de dados

Recomenda-se utilizar Docker para executar uma instância do PostgreSQL.

```bash
docker container run \
  --name fm-postgres \
  -e POSTGRES_DB=faturamento-manager \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  -d postgres
```

## 2. Configurar a aplicação

Verifique se o arquivo `application.properties` (ou `application.yml`) possui a seguinte configuração:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/faturamento-manager
spring.datasource.username=postgres
spring.datasource.password=postgres
```

## 3. Executar a aplicação

Utilizando Maven:

```bash
./mvnw spring-boot:run
```

Ou:

```bash
mvn spring-boot:run
```

A aplicação ficará disponível em:

```
http://localhost:8080
```

---

# Recursos da API

## Clientes

### Criar cliente

**POST**

```
/clientes
```

Exemplo de requisição:

```json
{
    "nome": "João da Silva",
    "email": "joao@email.com"
}
```

### Buscar cliente por ID

**GET**

```
/clientes/{id}
```

### Listar clientes

**GET**

```
/clientes
```

---

## Pedidos

### Criar pedido

**POST**

```
/pedidos
```

Exemplo:

```json
{
    "data_pedido": "2026-07-26T19:00:00",
    "id_cliente": 1
}
```

### Buscar pedido

**GET**

```
/pedidos/{id}
```

### Listar pedidos

Permite filtros opcionais.

**GET**

```
/pedidos
```

Filtros disponíveis:

| Parâmetro | Tipo |
|-----------|------|
| status | StatusEnum |
| idCliente | Long |

Exemplos:

```
GET /pedidos
```

```
GET /pedidos?status=ABERTO
```

```
GET /pedidos?idCliente=1
```

```
GET /pedidos?status=FINALIZADO&idCliente=1
```

---

### Adicionar itens ao pedido

**POST**

```
/pedidos/{id}/itens
```

Exemplo:

```json
[
    {
        "descricao": "Notebook",
        "quantidade": 1,
        "precoUnitario": 3500.00,
        "idPedido": 1
    },
    {
        "descricao": "Mouse",
        "quantidade": 2,
        "precoUnitario": 90.00,
        "idPedido": 1
    }
]
```

---

### Listar itens de um pedido

**GET**

```
/pedidos/{id}/itens
```

---

### Atualizar status do pedido

**PATCH**

```
/pedidos/{id}/status
```

Exemplo:

```json
{
    "status": "FINALIZADO"
}
```

---

### Remover pedido

**DELETE**

```
/pedidos/{id}
```

---

## Cartões de Fidelidade

### Criar cartão

**POST**

```
/cartoes-fidelidade
```

Exemplo:

```json
{
    "pontos": 100,
    "idCliente": 1
}
```

### Buscar cartão

**GET**

```
/cartoes-fidelidade/{id}
```

### Listar cartões

**GET**

```
/cartoes-fidelidade
```

---

# Modelo das entidades

## Cliente

| Campo | Tipo |
|--------|------|
| id | Long |
| nome | String |
| email | String |

## Pedido

| Campo | Tipo |
|--------|------|
| id | Long |
| data_pedido | LocalDateTime |
| id_cliente | Long |
| status | StatusEnum |
| itens | List<ItemDTO> |

## Item

| Campo | Tipo |
|--------|------|
| id | Long |
| descricao | String |
| quantidade | Integer |
| precoUnitario | BigDecimal |
| idPedido | Long |

## Cartão de Fidelidade

| Campo | Tipo |
|--------|------|
| id | Long |
| pontos | Integer |
| idCliente | Long |
