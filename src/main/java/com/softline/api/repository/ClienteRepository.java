package com.softline.api.repository;

import com.softline.api.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("""
        SELECT c FROM Cliente c
        WHERE unaccent(CAST(c.nome AS string)) ILIKE unaccent(CONCAT('%', :search, '%'))
           OR unaccent(CAST(c.fantasia AS string)) ILIKE unaccent(CONCAT('%', :search, '%'))
           OR unaccent(CAST(c.documento AS string)) ILIKE unaccent(CONCAT('%', :search, '%'))
           OR CAST(c.codigo AS string) LIKE CONCAT('%', :search, '%')
        """)
    Page<Cliente> search(String search, Pageable pageable);
}
