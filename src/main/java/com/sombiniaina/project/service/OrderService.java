package com.sombiniaina.project.service;

import com.sombiniaina.project.dto.request.CheckoutRequest;
import com.sombiniaina.project.dto.request.ProductOrderRequest;
import com.sombiniaina.project.exception.InsufficientStockException;
import com.sombiniaina.project.exception.OrderNotFoundException;
import com.sombiniaina.project.exception.ProductNotFoundException;
import com.sombiniaina.project.mapper.OrderMapper;
import com.sombiniaina.project.model.Order;
import com.sombiniaina.project.model.OrderStatus;
import com.sombiniaina.project.model.StockEpuiseEvent;
import com.sombiniaina.project.repository.OrderLineRepository;
import com.sombiniaina.project.repository.OrderRepository;
import com.sombiniaina.project.repository.ProductRepository;
import com.sombiniaina.project.repository.model.JOrder;
import com.sombiniaina.project.repository.model.JOrderLine;
import com.sombiniaina.project.repository.model.JProduct;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;
  private final OrderMapper orderMapper;
  private final OrderLineRepository orderLineRepository;
  private final ProductRepository productRepository;
  private final ApplicationEventPublisher applicationEventPublisher;

  @Transactional
  public Order checkout(CheckoutRequest request) {
    JOrder jOrder =
        JOrder.builder()
            .id(UUID.randomUUID())
            .customerEmail(request.getCustomerEmail())
            .totalPrice(BigDecimal.ZERO)
            .orderDate(Instant.now())
            .status(OrderStatus.PENDING)
            .build();

    jOrder = orderRepository.save(jOrder);

    BigDecimal runningTotalPrice = BigDecimal.ZERO;
    List<JOrderLine> savedLines = new ArrayList<>();

    for (ProductOrderRequest itemRequest : request.getItems()) {
      JProduct jProduct =
          productRepository
              .findWithLockById(itemRequest.getProductId())
              .orElseThrow(
                  () ->
                      new ProductNotFoundException(
                          "Product not found with id " + itemRequest.getProductId()));

      if (jProduct.getStockQuantity() < itemRequest.getQuantity()) {
        throw new InsufficientStockException(
            "Insufficient stock for item "
                + jProduct.getName()
                + ".Available : "
                + jProduct.getStockQuantity()
                + ", Requested : "
                + itemRequest.getQuantity());
      }

      jProduct.setStockQuantity(jProduct.getStockQuantity() - itemRequest.getQuantity());
      productRepository.save(jProduct);

      if (jProduct.getStockQuantity() == 0) {
        StockEpuiseEvent stockEpuiseEvent =
            new StockEpuiseEvent(jProduct.getId(), jProduct.getName());
        applicationEventPublisher.publishEvent(stockEpuiseEvent);
      }

      BigDecimal itemSubTotal =
          jProduct.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
      runningTotalPrice = runningTotalPrice.add(itemSubTotal);

      JOrderLine jOrderLine =
          JOrderLine.builder()
              .id(UUID.randomUUID())
              .order(jOrder)
              .product(jProduct)
              .quantity(itemRequest.getQuantity())
              .unitPrice(jProduct.getPrice())
              .build();

      savedLines.add(orderLineRepository.save(jOrderLine));
    }

    jOrder.setTotalPrice(runningTotalPrice);

    jOrder.setLines(savedLines);

    jOrder = orderRepository.save(jOrder);

    return orderMapper.toModel(jOrder);
  }

  @Transactional
  public Order cancelOrder(UUID orderId) {
    JOrder jOrder =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException("Order not found"));

    if (jOrder.getStatus() == OrderStatus.CANCELLED || jOrder.getStatus() == OrderStatus.SHIPPED) {
      throw new IllegalStateException("Cannot cancel an order that's already cancelled or shipped");
    }

    jOrder.setStatus(OrderStatus.CANCELLED);

    for (JOrderLine jOrderLine : jOrder.getLines()) {
      UUID targetProductId = jOrderLine.getProduct().getId();

      JProduct jProduct =
          productRepository
              .findWithLockById(targetProductId)
              .orElseThrow(
                  () ->
                      new ProductNotFoundException("Product not found with id " + targetProductId));
      jProduct.setStockQuantity(jProduct.getStockQuantity() + jOrderLine.getQuantity());
      productRepository.save(jProduct);
    }
    orderRepository.save(jOrder);
    return orderMapper.toModel(jOrder);
  }

  @Transactional(readOnly = true)
  public List<Order> getOrdersByCustomerEmail(String email) {
    List<JOrder> jOrders = orderRepository.findByCustomerEmail(email);

    if (jOrders.isEmpty()) {
      throw new OrderNotFoundException("Order not found");
    }

    return orderMapper.toModel(jOrders);
  }
}
