package com.sombiniaina.project.endpoint.rest.controller;

import com.sombiniaina.project.dto.request.ProductCreateRequest;
import com.sombiniaina.project.dto.request.ProductUpdateRequest;
import com.sombiniaina.project.model.Product;
import com.sombiniaina.project.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ProductController {
  private final ProductService productService;

  @GetMapping("/products")
  public ResponseEntity<List<Product>> getAllProducts(
      @RequestParam(required = false) String keyword,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(productService.getAllProducts(keyword, page, size));
  }

  @GetMapping("/products/new-arrivals")
  public ResponseEntity<?> newArrivals() {
    return ResponseEntity.ok(productService.getNewArrivals());
  }

  @PostMapping("/admin/products")
  public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductCreateRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(request));
  }

  @PutMapping("/admin/products/{id}")
  public ResponseEntity<Product> updateProduct(
      @PathVariable UUID id, @RequestBody @Valid ProductUpdateRequest request) {
    return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(id, request));
  }

  @DeleteMapping("/admin/products/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
    productService.deleteProduct(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
  }
}
