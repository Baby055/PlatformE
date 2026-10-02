package com.sombiniaina.project.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutRequest {
    @NotBlank(message = "Email is obligatory to order")
    @Email(message = "The email format is invalid")
    private String customerEmail;

    @NotEmpty(message = "Your shopping cart cannot be empty")
    @Valid
    private List<ProductOrderRequest> items;
}
