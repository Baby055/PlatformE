package com.sombiniaina.project.model;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record Product(
        String id,
        String name,
        BigDecimal price,
        long stockQuantity
){}
