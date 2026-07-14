package com.softline.api.repository;

import com.softline.api.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("""
        SELECT p FROM Produto p
        WHERE unaccent(CAST(p.descricao AS string)) ILIKE unaccent(CONCAT('%', :search, '%'))
           OR unaccent(CAST(p.codigoBarras AS string)) ILIKE unaccent(CONCAT('%', :search, '%'))
           OR CAST(p.codigo AS string) LIKE CONCAT('%', :search, '%')
        """)
    Page<Produto> search(String search, Pageable pageable);

    @Query("SELECT COALESCE(SUM(p.valorVenda), 0) FROM Produto p")
    BigDecimal sumValorVenda();
}
