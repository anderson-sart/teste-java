CREATE TABLE IF NOT EXISTS grupo_produto (
    codigo        BIGSERIAL PRIMARY KEY,
    descricao     VARCHAR(60) NOT NULL,
    created_at    TIMESTAMP DEFAULT NOW(),
    updated_at    TIMESTAMP DEFAULT NOW()
);

ALTER TABLE produtos ADD COLUMN grupo_produto_codigo BIGINT REFERENCES grupo_produto(codigo);
CREATE INDEX idx_produtos_grupo_produto ON produtos(grupo_produto_codigo);
