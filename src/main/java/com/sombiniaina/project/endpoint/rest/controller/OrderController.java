package com.sombiniaina.project.endpoint.rest.controller;

import com.sombiniaina.project.dto.request.CheckoutRequest;
import com.sombiniaina.project.exception.InsufficientStockException;
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
  public ResponseEntity<?> checkout(@RequestBody @Valid CheckoutRequest request) {
    try {
      Order order = orderService.checkout(request);
      return ResponseEntity.status(HttpStatus.OK).body(order);
    } catch (InsufficientStockException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (RuntimeException e) {
      if (e.getMessage().contains("Product not found")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
      }
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }
}
