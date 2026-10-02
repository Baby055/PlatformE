package com.sombiniaina.project.model;

import lombok.Builder;

import java.util.UUID;

@Builder
public record StockEpuiseEvent (UUID id, String producName){}
