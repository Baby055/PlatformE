package com.sombiniaina.project.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckoutRequest {
  @NotBlank(message = "Email is obligatory to order")
  @Email(message = "The email format is invalid")
  private String customerEmail;

  @NotEmpty(message = "Your shopping cart cannot be empty")
  @Valid
  private List<ProductOrderRequest> items;
}
