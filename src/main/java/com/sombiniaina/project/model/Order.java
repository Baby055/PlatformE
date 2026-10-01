package com.sombiniaina.project.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record Order(UUID id, String customerEmail, BigDecimal totalPrice, Instant orderDate, List<OrderLine> lines) {}
