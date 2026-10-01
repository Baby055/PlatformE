package com.sombiniaina.project.repository.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.*;

@Entity
@Table(name = "order")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JOrder {
  @Id private String id;

  @NotNull @Email private String customerEmail;

  @Positive private BigDecimal totalPrice;

  private Instant orderDate;
}
