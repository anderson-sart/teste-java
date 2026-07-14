package com.softline.api.dto;

import jakarta.validation.constraints.*;

public record ClienteRequest(
        @NotBlank @Size(max = 60) String nome,
        @Size(max = 100) String fantasia,
        @NotBlank @Size(max = 18) String documento,
        String endereco
) {}
