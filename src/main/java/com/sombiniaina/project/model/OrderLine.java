package com.sombiniaina.project.model;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Builder;

@Builder
public record OrderLine(UUID id, int quantity, BigDecimal unitPrice) {}
