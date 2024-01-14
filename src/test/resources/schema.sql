CREATE TABLE CLIENTE (
                         ID_CLIENTE INT AUTO_INCREMENT PRIMARY KEY,
                         NOME_CLIENTE VARCHAR(255) DEFAULT NULL
);

CREATE TABLE PEDIDO (
                        ID_PEDIDO INT AUTO_INCREMENT PRIMARY KEY,
                        DATA_CADASTRO DATE DEFAULT CURRENT_DATE,
                        NOME_PRODUTO VARCHAR(255) DEFAULT NULL,
                        NUMERO_CONTROLE VARCHAR(255) UNIQUE,
                        QUANTIDADE INT DEFAULT 1,
                        VALOR_TOTAL DOUBLE DEFAULT NULL,
                        VALOR_UNITARIO DOUBLE DEFAULT NULL,
                        ID_CLIENTE INT,
                        FOREIGN KEY (ID_CLIENTE) REFERENCES CLIENTE (ID_CLIENTE)
);