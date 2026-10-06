package com.sombiniaina.project.service;

import com.sombiniaina.project.model.ProductBackInStockEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ProductEventConsumer {
    @Async
    @EventListener
    public void onProductBackInStock(ProductBackInStockEvent event) {
        System.out.println("This product" + event.productName() + " has been back in stock");
    }
}
