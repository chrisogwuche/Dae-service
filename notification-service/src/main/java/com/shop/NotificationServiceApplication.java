package com.shop;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
public class NotificationServiceApplication {
    private JsonMapper jsonMapper;

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @KafkaListener(topics = "notificationTopic",id = "notificationId")
    public void handleNotification(OrderPlacedEvent orderPlacedEvent){
        //send out an email notification

        log.info("Received Notification for Order -{}", orderPlacedEvent.getOrderNumber());
    }

}
