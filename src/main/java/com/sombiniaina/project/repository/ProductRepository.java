package com.sombiniaina.project.repository;

import com.sombiniaina.project.repository.model.JProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<JProduct, UUID> {
    Optional<JProduct> findById(UUID id);

    List<JProduct> findByNameContainingIgnoreCase(String keyword);
}
