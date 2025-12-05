package com.kafka.demo.listener;

import com.kafka.demo.store.MessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @Autowired
    private MessageStore store;

    @KafkaListener(topics = "test-topic", groupId = "test-group")
    public void listen(String message) {
        System.out.println("Received message: " + message);
        if (store != null) {
            store.add(message);
        }
    }
}
