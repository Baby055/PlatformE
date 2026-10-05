package com.sombiniaina.project.service;

import com.sombiniaina.project.mapper.ProductMapper;
import com.sombiniaina.project.model.Product;
import com.sombiniaina.project.repository.ProductRepository;
import com.sombiniaina.project.repository.model.JProduct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    public List<Product> getNewArrivals(){
        Instant sevenDaysAgo = Instant.now().minus(7, java.time.temporal.ChronoUnit.DAYS);

        List<JProduct> newArrival = productRepository.findByCreationDateAfter(sevenDaysAgo);

        return productMapper.toModel(newArrival);
    }
}
