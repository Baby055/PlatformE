package com.sombiniaina.project.service;

import com.sombiniaina.project.model.StockEpuiseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class StockEventConsumer {
  @Autowired private JavaMailSender mailSender;

  @Value("${app.admin.email}")
  private String adminEmail;

  @Async
  @EventListener
  public void handleStockEpuise(StockEpuiseEvent event) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(adminEmail);
    message.setSubject("Stock Epuise Information");
    message.setText("Greetings Admin , the stock for" + event.productName() + "is depleted");

    mailSender.send(message);
  }
}
