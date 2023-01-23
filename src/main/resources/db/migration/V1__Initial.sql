CREATE TABLE usuario (
    id VARCHAR2(255) NOT NULL PRIMARY KEY,
    nome VARCHAR2(255) NOT NULL,
    email VARCHAR2(200) NOT NULL,
    senha VARCHAR2(90) NOT NULL,
    data_criacao DATETIME(6) NOT NULL,
    data_exclusao DATETIME(6) NULL
);