package com.sombiniaina.project.mapper;

import com.sombiniaina.project.model.OrderLine;
import com.sombiniaina.project.repository.model.JOrder;
import com.sombiniaina.project.repository.model.JOrderLine;
import com.sombiniaina.project.repository.model.JProduct;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class OrderLineMapper {

  public List<OrderLine> toModel(List<JOrderLine> entities) {
    return entities.stream().map(this::toModel).toList();
  }

  public OrderLine toModel(JOrderLine entity) {
    return OrderLine.builder()
        .id(entity.getId())
        .quantity(entity.getQuantity())
        .unitPrice(entity.getUnitPrice())
        .productId(entity.getProduct().getId())
        .productName(entity.getProduct().getName())
        .build();
  }

  public JOrderLine toEntity(OrderLine domain, JOrder jOrder, JProduct jProduct) {
    return JOrderLine.builder()
        .id(domain.id())
        .order(jOrder)
        .product(jProduct)
        .quantity(domain.quantity())
        .unitPrice(domain.unitPrice())
        .build();
  }
}
