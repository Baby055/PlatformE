package com.sombiniaina.project.model;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record Product(String id, String name, BigDecimal price, long stockQuantity) {}
