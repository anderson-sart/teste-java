package com.softline.api.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProdutoRequest(
        @NotBlank @Size(max = 60) String descricao,
        @Size(max = 14) String codigoBarras,
        @NotNull @DecimalMin("0.0") BigDecimal valorVenda,
        @NotNull @DecimalMin("0.0") BigDecimal pesoBruto,
        @NotNull @DecimalMin("0.0") BigDecimal pesoLiquido
) {}
