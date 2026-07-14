package com.softline.api.repository;

import com.softline.api.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query(value = """
        SELECT * FROM produtos
        WHERE deleted_at IS NULL
          AND (unaccent(descricao) ILIKE unaccent(CONCAT('%', :search, '%'))
            OR unaccent(COALESCE(codigo_barras,'')) ILIKE unaccent(CONCAT('%', :search, '%'))
            OR CAST(codigo AS TEXT) LIKE CONCAT('%', :search, '%'))
        """,
        countQuery = """
        SELECT COUNT(*) FROM produtos
        WHERE deleted_at IS NULL
          AND (unaccent(descricao) ILIKE unaccent(CONCAT('%', :search, '%'))
            OR unaccent(COALESCE(codigo_barras,'')) ILIKE unaccent(CONCAT('%', :search, '%'))
            OR CAST(codigo AS TEXT) LIKE CONCAT('%', :search, '%'))
        """,
        nativeQuery = true)
    Page<Produto> search(String search, Pageable pageable);

    @Query(value = "SELECT COALESCE(SUM(valor_venda), 0) FROM produtos WHERE deleted_at IS NULL", nativeQuery = true)
    BigDecimal sumValorVenda();
}
