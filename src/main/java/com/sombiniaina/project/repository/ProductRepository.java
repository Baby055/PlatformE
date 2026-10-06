package com.sombiniaina.project.repository;

import com.sombiniaina.project.repository.model.JProduct;
import jakarta.persistence.LockModeType;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<JProduct, UUID> {
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<JProduct> findWithLockById(UUID id);

  List<JProduct> findByCreationDateAfter(Instant creationDate);

  Page<JProduct> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
}
