package com.sombiniaina.project.repository;

import com.sombiniaina.project.repository.model.JOrderLine;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineRepository extends JpaRepository<JOrderLine, UUID> {
  List<JOrderLine> findByOrderId(UUID orderId);
}
