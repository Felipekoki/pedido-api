-- Drop table if exists for PEDIDO
DROP TABLE IF EXISTS `PEDIDO`;

-- Drop table if exists for CLIENTE
DROP TABLE IF EXISTS `CLIENTE`;

-- Criar tabela CLIENTE
CREATE TABLE `CLIENTE` (
       `ID_CLIENTE` int NOT NULL AUTO_INCREMENT,
       `NOME_CLIENTE` varchar(255) DEFAULT NULL,
       PRIMARY KEY (`ID_CLIENTE`),
       UNIQUE KEY `UK_8trt5abgnrr4st7sveld8xiis` (`NOME_CLIENTE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Criar tabela PEDIDO
CREATE TABLE `PEDIDO` (
      `ID_PEDIDO` int NOT NULL AUTO_INCREMENT,
      `DATA_CADASTRO` date DEFAULT (CURRENT_DATE),
      `NOME_PRODUTO` varchar(255) DEFAULT NULL,
      `NUMERO_CONTROLE` varchar(255) DEFAULT NULL,
      `QUANTIDADE` int DEFAULT 1,
      `VALOR_TOTAL` double DEFAULT NULL,
      `VALOR_UNITARIO` double DEFAULT NULL,
      `ID_CLIENTE` int DEFAULT NULL,
      PRIMARY KEY (`ID_PEDIDO`),
      UNIQUE KEY `UK_66ndy1l4vsfnjmxbmedw6h6nc` (`NUMERO_CONTROLE`),
      KEY `FKc3swyledhfki1g7voc04cvxml` (`ID_CLIENTE`),
      CONSTRAINT `FKc3swyledhfki1g7voc04cvxml` FOREIGN KEY (`ID_CLIENTE`) REFERENCES `CLIENTE` (`ID_CLIENTE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Adicionar índice para a coluna NUMERO_CONTROLE
ALTER TABLE `PEDIDO`
    ADD INDEX `IDX_NUMERO_CONTROLE` (`NUMERO_CONTROLE`);

-- Adicionar índice para a coluna DATA_CADASTRO
ALTER TABLE `PEDIDO`
    ADD INDEX `IDX_DATA_CADASTRO` (`DATA_CADASTRO`);

-- Adicionar Clientes Default
INSERT INTO CLIENTE (NOME_CLIENTE) VALUES
                                       ('Cliente 1'),
                                       ('Cliente 2'),
                                       ('Cliente 3'),
                                       ('Cliente 4'),
                                       ('Cliente 5'),
                                       ('Cliente 6'),
                                       ('Cliente 7'),
                                       ('Cliente 8'),
                                       ('Cliente 9'),
                                       ('Cliente 10');