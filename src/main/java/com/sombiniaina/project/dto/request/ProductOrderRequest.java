package com.sombiniaina.project.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductOrderRequest {
  @NotBlank(message = "Poduct ID can't be null")
  private UUID productId;

  @Min(value = 1, message = "Quantity must be at least 1")
  private int quantity;
}
