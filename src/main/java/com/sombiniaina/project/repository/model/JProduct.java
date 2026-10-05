package com.sombiniaina.project.repository.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JProduct {
  @Id private UUID id;

  @NotNull private String name;

  @Positive private BigDecimal price;

  @Positive private long stockQuantity;

  @NotNull private Instant creationDate;
}
