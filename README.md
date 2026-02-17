# Granja API (Desafio Back End Java)

API REST em **Java + Spring Boot** para gerenciamento de uma granja de patos: cadastro de patos, clientes e vendedores, registro de vendas, listagem de patos vendidos, geração de relatório em Excel e ranking de vendedores.

---

## Tecnologias

- **Java 17**
- **Spring Boot 4**
- **Spring Web (MVC)**
- **Spring Data JPA (Hibernate)**
- **MySQL 8**
- **Flyway** (migrations do banco)
- **Apache POI** (geração de Excel)
- **Docker / Docker Compose**

---

## Como executar

### Opção 1 — Docker Compose (recomendado)

Subir aplicação + MySQL:

A API ficará disponível em:

- `http://localhost:8080`

Para resetar tudo (incluindo dados do MySQL):

---

### Opção 2 — Rodar local (sem Docker)

Pré-requisitos:
- Java 17
- MySQL rodando localmente
- Maven (ou Maven Wrapper)

Rodar:

---

## Banco de Dados (Flyway)

As migrations ficam em:

- `src/main/resources/db/migration/`

Arquivos:
- `V1__create_cliente.sql`
- `V2__create_vendedor.sql`
- `V3__create_venda.sql`
- `V4__create_pato.sql`

---

## Postman Collection (pronta)

Uma collection completa para testar a API está disponível em:

- `src/main/resources/requisitos/Granja API - Completa (2025).json`

Basta importar no Postman e executar as requisições.

---

## Endpoints principais

### Patos
- `POST /patos` — cadastra pato (com opção de mãe)
- `GET /patos` — pesquisa  
  **Query params (opcionais):** `id`, `nmPato`, `idPatoMae`
- `GET /patos/{id}` — busca por id
- `PUT /patos/{id}` — atualiza
- `DELETE /patos/{id}` — desativa
- `GET /patos/vendidos` — lista patos vendidos (data da venda, valor total líquido da venda e cliente)

### Clientes
- `POST /clientes` — cadastra cliente (com/sem desconto)
- `GET /clientes` — pesquisa  
  **Query params (opcionais):** `id`, `nmPato`, `stValidoDesconto`

> Observação: o parâmetro `nmPato` no endpoint de clientes é um nome legado no DTO/Controller, mas representa o filtro por nome do cliente.

### Vendedores
- `POST /vendedores` — cadastra vendedor (CPF único)
- `GET /vendedores` — pesquisa  
  **Query params (opcionais):** `id`, `nmVendedor`, `dsCpf`, `dsMatricula`
- `DELETE /vendedores/{id}` — exclui vendedor (bloqueia se já realizou vendas)

### Vendas
- `POST /vendas` — registra venda (1+ patos) com:
    - desconto de 20% se cliente elegível
    - data automática
    - bloqueio de venda de pato indisponível

- `GET /vendas/relatorio/excel` — baixa relatório Excel  
  **Query params (obrigatórios):** `dtInicial` e `dtFinal` no formato `dd/MM/yyyy`

Exemplo (download do Excel):

- `GET /vendas/ranking` — ranking de vendedores  
  **Query params (obrigatórios):** `dtInicial`, `dtFinal` (`dd/MM/yyyy`)  
  **Query params (opcionais):** `statusVendaPato` (`VENDIDO` ou `DISPONIVEL`), `ordenacao` (`VALOR` ou `QTDE`)

---

## Regras de preço (por pato)

- Pato **sem filhos**: **R$ 70,00**
- Pato com **1 filho**: **R$ 50,00**
- Pato com **2 ou mais filhos**: **R$ 25,00**

---

## Observações

- O schema do banco é gerenciado pelo **Flyway**.
- O relatório Excel é gerado via **Apache POI** e segue o layout definido no desafio.
