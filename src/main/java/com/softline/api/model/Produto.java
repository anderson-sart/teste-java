package com.softline.api.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "produtos")
@SQLRestriction("deleted_at IS NULL")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Column(nullable = false, length = 60)
    private String descricao;

    @Column(name = "codigo_barras", length = 14)
    private String codigoBarras;

    @Column(name = "valor_venda", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorVenda;

    @Column(name = "peso_bruto", nullable = false, precision = 10, scale = 3)
    private BigDecimal pesoBruto;

    @Column(name = "peso_liquido", nullable = false, precision = 10, scale = 3)
    private BigDecimal pesoLiquido;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grupo_produto_codigo")
    private GrupoProduto grupoProduto;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    void prePersist() { createdAt = updatedAt = LocalDateTime.now(); }

    @PreUpdate
    void preUpdate() { updatedAt = LocalDateTime.now(); }
}
