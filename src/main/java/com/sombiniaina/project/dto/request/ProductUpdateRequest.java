package com.sombiniaina.project.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductUpdateRequest {
  @NotBlank(message = "Product name can't be blank")
  private String name;

  @Positive
  @NotNull(message = "Price can't be null")
  private BigDecimal price;

  @Positive
  @NotNull(message = "Quantity can't be null")
  private long quantity;
}
