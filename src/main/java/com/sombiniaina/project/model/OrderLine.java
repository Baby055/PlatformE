package com.sombiniaina.project.model;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderLine (
        String id,
        int quantity,
        BigDecimal unitPrice
){}
