package com.sombiniaina.project.service;

import com.sombiniaina.project.exception.ProductNotFoundException;
import com.sombiniaina.project.mapper.ProductMapper;
import com.sombiniaina.project.model.Product;
import com.sombiniaina.project.model.ProductBackInStockEvent;
import com.sombiniaina.project.repository.ProductRepository;
import com.sombiniaina.project.repository.model.JProduct;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class InventoryService {
  private final ProductRepository productRepository;
  private final ProductMapper productMapper;
  private final ApplicationEventPublisher applicationEventPublisher;

  @Transactional
  public Product restockAndRevalue(UUID productId, int addedQuantity, BigDecimal newBasePrice) {
    JProduct jProduct =
        productRepository
                .findWithLockById(productId)
            .orElseThrow(
                () -> new ProductNotFoundException("Product not found with id " + productId));
    long initialStock = jProduct.getStockQuantity();
    jProduct.setStockQuantity(initialStock + addedQuantity);

    if (addedQuantity > 100) {
      BigDecimal discount = newBasePrice.multiply(BigDecimal.valueOf(0.10));
      jProduct.setPrice(newBasePrice.subtract(discount));
    } else {
      jProduct.setPrice(newBasePrice);
    }
    productRepository.save(jProduct);

    if (initialStock == 0 && (initialStock + addedQuantity) > 0) {
      ProductBackInStockEvent productBackInStockEvent =
          new ProductBackInStockEvent(jProduct.getId(), jProduct.getName());
      applicationEventPublisher.publishEvent(productBackInStockEvent);
    }

    return productMapper.toModel(jProduct);
  }
}
