package com.sombiniaina.project.repository;

import com.sombiniaina.project.repository.model.JOrder;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<JOrder, UUID> {
  Optional<JOrder> findById(UUID id);

  Optional<JOrder> findByCustomerEmail(String email);
}
