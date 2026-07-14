package com.softline.api.repository;

import com.softline.api.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query(value = """
        SELECT * FROM clientes
        WHERE deleted_at IS NULL
          AND (unaccent(nome) ILIKE unaccent(CONCAT('%', :search, '%'))
            OR unaccent(COALESCE(fantasia,'')) ILIKE unaccent(CONCAT('%', :search, '%'))
            OR unaccent(documento) ILIKE unaccent(CONCAT('%', :search, '%'))
            OR CAST(codigo AS TEXT) LIKE CONCAT('%', :search, '%'))
        """,
        countQuery = """
        SELECT COUNT(*) FROM clientes
        WHERE deleted_at IS NULL
          AND (unaccent(nome) ILIKE unaccent(CONCAT('%', :search, '%'))
            OR unaccent(COALESCE(fantasia,'')) ILIKE unaccent(CONCAT('%', :search, '%'))
            OR unaccent(documento) ILIKE unaccent(CONCAT('%', :search, '%'))
            OR CAST(codigo AS TEXT) LIKE CONCAT('%', :search, '%'))
        """,
        nativeQuery = true)
    Page<Cliente> search(String search, Pageable pageable);
}
