package com.sombiniaina.project.model;

import java.util.UUID;

public record ProductBackInStockEvent (UUID productId, String productName){}
