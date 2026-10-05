package com.sombiniaina.project.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.Builder;

@Builder
public record Product(
    UUID id, String name, BigDecimal price, long stockQuantity, Instant creationDate) {}
