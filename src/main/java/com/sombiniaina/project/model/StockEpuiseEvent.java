package com.sombiniaina.project.model;

import java.util.UUID;
import lombok.Builder;

@Builder
public record StockEpuiseEvent(UUID id, String productName) {}
