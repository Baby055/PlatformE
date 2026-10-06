package com.sombiniaina.project.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCreateRequest {
    @NotBlank(message = "Name can't be a blank")
    private String name;

    @Positive
    @NotNull(message = "Price can't be null")
    private BigDecimal price;

    @Positive
    @NotNull(message = "Quantity can't be null")
    private long stockQuantity;
}
