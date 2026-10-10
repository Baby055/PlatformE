package com.sombiniaina.project.repository.model;

import com.sombiniaina.project.model.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JOrder {
  @Id private UUID id;

  @NotNull @Email private String customerEmail;

  @PositiveOrZero private BigDecimal totalPrice;

  private Instant orderDate;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<JOrderLine> lines;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;
}
