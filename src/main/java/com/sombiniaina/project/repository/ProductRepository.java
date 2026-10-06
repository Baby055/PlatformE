package com.sombiniaina.project.repository;

import com.sombiniaina.project.repository.model.JProduct;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<JProduct, UUID> {
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<JProduct> findWithLockById(UUID id);

  List<JProduct> findByCreationDateAfter(Instant creationDate);
}
