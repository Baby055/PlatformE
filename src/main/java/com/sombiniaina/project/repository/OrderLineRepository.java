package com.sombiniaina.project.repository;

import com.sombiniaina.project.repository.model.JOrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderLineRepository extends JpaRepository<JOrderLine, UUID> {
    Optional<JOrderLine> findById(UUID uuid);

    List<JOrderLine> findByOrderId(UUID orderId);
}
