# Pedido API

**Descrição:**

A Pedido API é uma aplicação Java que fornece serviços para a recepção e consulta de pedidos de clientes. A aplicação atende aos requisitos de recepção de pedidos no formato XML e JSON, com validações e manipulações específicas. Além disso, oferece serviços para consultar os pedidos enviados pelos clientes.

## Requisitos

- JDK 1.8 ou versão mais recente
- Maven
- Docker (opcional, se desejar rodar a aplicação em contêineres)

### Configuração do Banco de Dados (MySQL)

A aplicação utiliza um banco de dados MySQL. Certifique-se de ter um servidor MySQL em execução e configure as seguintes propriedades no arquivo `application.properties`:

```properties
spring.datasource.url=[SEU_SERVIDOR_MYSQL]
spring.datasource.username=[SEU_USUARIO]
spring.datasource.password=[SUA_SENHA]
