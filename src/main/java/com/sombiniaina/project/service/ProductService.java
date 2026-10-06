package com.sombiniaina.project.service;

import com.sombiniaina.project.dto.request.ProductCreateRequest;
import com.sombiniaina.project.dto.request.ProductUpdateRequest;
import com.sombiniaina.project.exception.ProductNotFoundException;
import com.sombiniaina.project.mapper.ProductMapper;
import com.sombiniaina.project.model.Product;
import com.sombiniaina.project.repository.ProductRepository;
import com.sombiniaina.project.repository.model.JProduct;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  public Product createProduct(ProductCreateRequest request) {
    JProduct jProduct =
        JProduct.builder()
            .id(UUID.randomUUID())
            .name(request.getName())
            .stockQuantity(request.getStockQuantity())
            .price(request.getPrice())
            .creationDate(Instant.now())
            .build();
    return productMapper.toModel(productRepository.save(jProduct));
  }

  public List<Product> getAllProducts(String keyword, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    String searchKeword = (keyword == null) ? "" : keyword;
    Page<JProduct> jProducts =
        productRepository.findByNameContainingIgnoreCase(searchKeword, pageable);
    return productMapper.toModel(jProducts.getContent());
  }

  public Product updateProduct(UUID id, ProductUpdateRequest request) {
    JProduct jProduct =
        productRepository
            .findById(id)
            .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    jProduct.setName(request.getName());
    jProduct.setPrice(request.getPrice());
    jProduct.setStockQuantity(request.getQuantity());
    return productMapper.toModel(productRepository.save(jProduct));
  }

  public void deleteProduct(UUID id) {
    if (productRepository.findById(id).isEmpty()) {
      throw new ProductNotFoundException("Product not found");
    }
    productRepository.deleteById(id);
  }

  @Transactional
  public List<Product> getNewArrivals() {
    Instant sevenDaysAgo = Instant.now().minus(7, java.time.temporal.ChronoUnit.DAYS);

    List<JProduct> newArrival = productRepository.findByCreationDateAfter(sevenDaysAgo);

    return productMapper.toModel(newArrival);
  }
}
