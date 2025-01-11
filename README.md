# API de Veículos

Este projeto consiste em duas APIs (api_1 e api_2) que trabalham em conjunto para buscar, processar e armazenar dados de veículos utilizando a API da FIPE. As APIs são desenvolvidas utilizando Spring Boot e se comunicam através de filas RabbitMQ.

## Estrutura do Projeto

```
.idea/
api_1/
    API_1/
        .mvn/
        src/
        target/
api_2/
    API_2/
        .mvn/
        src/
        target/
test/
```

### API 1

A api_1 é responsável por buscar dados da API da FIPE e enviar esses dados para uma fila RabbitMQ.

#### Endpoints

- **Carga Inicial de Dados**
  - **URL:** `/api/veiculos/carga-inicial`
  - **Método:** GET


  - **Descrição:** Carrega a lista de veículos para a fila.

- **Buscar Todas as Marcas**
  - **URL:** `/api/veiculos/todas-marcas`
  - **Método:** GET


  - **Descrição:** Retorna uma lista de veículos de todas as marcas.

- **Buscar Veículos por Marca**
  - **URL:** `/api/veiculos/por-marca`
  - **Método:** GET
  - **Parâmetros:** marca(String)
  - **Descrição:** Retorna uma lista de veículos com base na marca fornecida.

- **Atualizar Veículo**
  - **URL:** `/api/veiculos/{id}`
  - **Método:** `PUT`
  - **Parâmetros:** id(Long), fipeCarrosAtualizado(JSON)
  - **Descrição:** Atualiza o modelo e observações de um veículo baseado no ID fornecido.

#### Configurações

As configurações da api_1 estão no arquivo application.properties.

### API 2

A api_2 é responsável por consumir os dados da fila RabbitMQ e armazená-los no banco de dados.

#### Configurações

As configurações da api_2 estão no arquivo application.properties.

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- RabbitMQ
- MySQL
- Swagger (OpenAPI)

## Como Executar

### Pré-requisitos

- JDK 17
- Maven
- MySQL
- RabbitMQ

### Passos

1. Clone o repositório.
2. Configure o banco de dados MySQL e RabbitMQ conforme os arquivos 

application.properties

.
3. Navegue até o diretório 

API_1 e execute:

   ```sh
   ./mvnw spring-boot:run
   ```
4. Navegue até o diretório API_2 e execute:

   ```sh
   ./mvnw spring-boot:run
   ```

## Testes

Para executar os testes, navegue até o diretório da API desejada (API_1 ou API_2) e execute:

```sh
./mvnw test
```

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests.

## Licença

Este projeto está licenciado sob a Licença Apache 2.0. Veja o arquivo LICENSE para mais detalhes.
