CREATE TABLE usuario (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(200) NOT NULL,
    senha VARCHAR(90) NOT NULL,
    data_criacao DATETIME(6) NOT NULL,
    data_exclusao DATETIME(6) NULL
);