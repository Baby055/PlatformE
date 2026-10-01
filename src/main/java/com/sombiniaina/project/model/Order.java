package com.sombiniaina.project.model;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
public record Order (
        String id,
        String customerEmail,
        BigDecimal totalPrice,
        Instant orderDate
){}
