package com.sombiniaina.project.endpoint.rest.controller;

import com.sombiniaina.project.dto.request.CheckoutRequest;
import com.sombiniaina.project.model.Order;
import com.sombiniaina.project.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class OrderController {
  private final OrderService orderService;

  @GetMapping("/admin/orders")
  public ResponseEntity<List<Order>> getOrdersByCustomer(@RequestParam String email) {
    return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrdersByCustomerEmail(email));
  }

  @PostMapping("/orders/checkout")
  public ResponseEntity<Order> checkout(@RequestBody @Valid CheckoutRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(orderService.checkout(request));
  }

  @PutMapping("/admin/orders/{id}/cancel")
  public ResponseEntity<Order> cancelOrder(@PathVariable UUID id){
    return ResponseEntity.status(HttpStatus.OK).body(orderService.cancelOrder(id));
  }
}
