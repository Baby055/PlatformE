package com.sombiniaina.project.mapper;

import com.sombiniaina.project.model.Order;
import com.sombiniaina.project.model.OrderLine;
import com.sombiniaina.project.repository.model.JOrder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class OrderMapper {
    private final OrderLineMapper orderLineMapper;

    public List<Order> toModel(List<JOrder> jOrders) {
        return jOrders.stream().map(this::toModel).toList();
    }

    public Order toModel(JOrder jOrder) {
        return Order.builder()
                .id(jOrder.getId())
                .customerEmail(jOrder.getCustomerEmail())
                .totalPrice(jOrder.getTotalPrice())
                .orderDate(jOrder.getOrderDate())
                .lines(orderLineMapper.toModel(jOrder.getLines()))
                .build();
    }

    public List<JOrder> toEntity(List<Order> orders) {
        return orders.stream().map(this::toEntity).toList();
    }

    public JOrder toEntity(Order order) {
        return JOrder.builder()
                .id(order.id())
                .customerEmail(order.customerEmail())
                .totalPrice(order.totalPrice())
                .orderDate(order.orderDate())
                .build();
    }
}
