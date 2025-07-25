-- V1__create_tables.sql

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE marca (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       nome VARCHAR(255) NOT NULL
);

CREATE TABLE modelo (
                        id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                        nome VARCHAR(255) NOT NULL,
                        marca_id UUID NOT NULL,
                        CONSTRAINT fk_modelo_marca FOREIGN KEY (marca_id) REFERENCES marca(id)
);

CREATE TABLE veiculo (
                         id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                         placa VARCHAR(10) NOT NULL,
                         preco_anuncio NUMERIC(10, 2) NOT NULL,
                         ano INT NOT NULL,
                         preco_fipe NUMERIC(10, 2) NOT NULL,
                         data_cadastro TIMESTAMP NOT NULL,
                         modelo_id UUID NOT NULL,
                         CONSTRAINT fk_veiculo_modelo FOREIGN KEY (modelo_id) REFERENCES modelo(id)
);
