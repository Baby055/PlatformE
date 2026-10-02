package com.sombiniaina.project.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderRequest {
    @NotBlank(message = "Poduct ID can't be null")
    private UUID productId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
}
