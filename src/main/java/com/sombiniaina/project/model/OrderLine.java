package com.sombiniaina.project.model;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record OrderLine(String id, int quantity, BigDecimal unitPrice) {}
