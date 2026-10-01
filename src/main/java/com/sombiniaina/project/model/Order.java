package com.sombiniaina.project.model;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;

@Builder
public record Order(String id, String customerEmail, BigDecimal totalPrice, Instant orderDate) {}
