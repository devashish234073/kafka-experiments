package com.kafka.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/stream")
public class StreamController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final AtomicLong sequence = new AtomicLong(0);
    private Thread streamThread;

    @Value("${app.topic}")
    private String topic;

    public StreamController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/start")
    public String startStream() {
        if (running.get()) {
            return "Stream already running";
        }
        running.set(true);
        streamThread = new Thread(() -> {
            while (running.get()) {
                try {
                    long id = sequence.incrementAndGet();
                    String msg = "Seq-" + id;
                    kafkaTemplate.send(topic, msg);
                    Thread.sleep(100); // 10 messages per second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    System.err.println("Error sending message: " + e.getMessage());
                }
            }
        });
        streamThread.start();
        return "Stream started";
    }

    @PostMapping("/stop")
    public String stopStream() {
        running.set(false);
        if (streamThread != null) {
            try {
                streamThread.join(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return "Stream stopped. Last sequence: " + sequence.get();
    }
}
