# API - Faturamento manager

## Documentações
- Diagrama de entidade relacionamento

## Como executar a aplicação
- Primeiro, o usuario deverá criar uma base dados postgres, recomenda-se utilizar o docker para isso, usando o seguinte comando:

```bash
docker container run --name fm-postgres -e POSTGRES_DB=faturamento-manager -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres
```