package com.sombiniaina.project.endpoint.rest.controller;

import com.sombiniaina.project.dto.request.CheckoutRequest;
import com.sombiniaina.project.model.Order;
import com.sombiniaina.project.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class OrderController {
  private final OrderService orderService;

  @PostMapping("/orders/checkout")
  public ResponseEntity<Order> checkout(@RequestBody @Valid CheckoutRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(orderService.checkout(request));
  }
}
