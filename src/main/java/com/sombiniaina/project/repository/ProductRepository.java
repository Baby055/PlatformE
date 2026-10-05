package com.sombiniaina.project.repository;

import com.sombiniaina.project.repository.model.JProduct;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<JProduct, UUID> {
  Optional<JProduct> findById(UUID id);

  List<JProduct> findByNameContainingIgnoreCase(String keyword);

  Optional<JProduct> findByCreationDateAfter(Instant creationDate);
}
