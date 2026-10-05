package com.sombiniaina.project.endpoint.rest.controller;

import com.sombiniaina.project.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/products/new-arrivals")
    public ResponseEntity<?> newArrivals(){
        return ResponseEntity.ok(productService.getNewArrivals());
    }
}
