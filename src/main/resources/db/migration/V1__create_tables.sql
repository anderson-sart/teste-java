CREATE EXTENSION IF NOT EXISTS unaccent;

CREATE TABLE IF NOT EXISTS users (
    id        BIGSERIAL PRIMARY KEY,
    username  VARCHAR(50) NOT NULL UNIQUE,
    password  VARCHAR(255) NOT NULL,
    is_admin  BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS produtos (
    codigo        BIGSERIAL PRIMARY KEY,
    descricao     VARCHAR(60) NOT NULL,
    codigo_barras VARCHAR(14),
    valor_venda   NUMERIC(10,2) NOT NULL,
    peso_bruto    NUMERIC(10,3) NOT NULL,
    peso_liquido  NUMERIC(10,3) NOT NULL,
    deleted_at    TIMESTAMP,
    created_at    TIMESTAMP DEFAULT NOW(),
    updated_at    TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS clientes (
    codigo     BIGSERIAL PRIMARY KEY,
    nome       VARCHAR(60) NOT NULL,
    fantasia   VARCHAR(100),
    documento  VARCHAR(18) NOT NULL,
    endereco   TEXT,
    deleted_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);
