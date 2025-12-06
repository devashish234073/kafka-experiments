package com.kafka.demo.controller;

import com.kafka.demo.store.MessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MessagesController {

    @Autowired
    private MessageStore store;

    @GetMapping("/messages")
    public List<String> messages() {
        return store.recent(50);
    }

    @GetMapping("/stats")
    public Map<String, Long> stats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalReceived", store.getTotalReceived());
        stats.put("lostMessages", store.getLostMessages());
        return stats;
    }
}
