package com.sombiniaina.project.mapper;

import com.sombiniaina.project.model.Product;
import com.sombiniaina.project.repository.model.JProduct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ProductMapper {
    public List<Product> toModel(List<JProduct> jProducts) {
        return jProducts.stream().map(this::toModel).toList();
    }

        public Product toModel(JProduct jProduct) {
        return Product.builder()
                .id(jProduct.getId())
                .name(jProduct.getName())
                .price(jProduct.getPrice())
                .stockQuantity(jProduct.getStockQuantity())
                .build();
    }

    public List<JProduct> toEntity(List<Product> products) {
        return products.stream().map(this::toEntity).toList();
    }

    public JProduct toEntity(Product product) {
        return JProduct.builder()
                .id(product.id())
                .name(product.name())
                .price(product.price())
                .stockQuantity(product.stockQuantity())
                .build();
    }
}
