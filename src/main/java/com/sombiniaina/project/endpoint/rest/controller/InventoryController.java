package com.sombiniaina.project.endpoint.rest.controller;

import com.sombiniaina.project.service.InventoryService;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class InventoryController {
  private final InventoryService inventoryService;

  @PutMapping("/admin/products/{id}/restock")
  public ResponseEntity<?> restockProduct(
      @PathVariable UUID id,
      @RequestParam int addedQuantity,
      @RequestParam BigDecimal newBasePrice) {
    return ResponseEntity.ok(inventoryService.restockAndRevalue(id, addedQuantity, newBasePrice));
  }
}
