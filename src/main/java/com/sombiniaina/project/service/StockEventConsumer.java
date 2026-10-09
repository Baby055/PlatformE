package com.sombiniaina.project.service;

import com.sombiniaina.project.mail.Email;
import com.sombiniaina.project.mail.Mailer;
import com.sombiniaina.project.model.StockEpuiseEvent;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockEventConsumer {
  private final Mailer mailer;

  @Value("${app.admin.email:noreply@poja.io}")
  private String adminEmail;

  @Async
  @EventListener
  public void handleStockEpuise(StockEpuiseEvent event) throws AddressException {
    mailer.accept(
        new Email(
            new InternetAddress(adminEmail),
            List.of(),
            List.of(),
            "Stock Epuise Information",
            "Greetings Admin , the stock for " + event.productName() + " is depleted",
            List.of()));
  }
}
