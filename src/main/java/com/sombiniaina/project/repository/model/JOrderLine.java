package com.sombiniaina.project.repository.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_line")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JOrderLine {
    @Id private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private JOrder order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private JProduct product;

    @Min(1)
    private int quantity;

    @Positive
    private BigDecimal unitPrice;
}
