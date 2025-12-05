package com.kafka.demo.controller;

import com.kafka.demo.store.MessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessagesController {

    @Autowired
    private MessageStore store;

    @GetMapping("/messages")
    public List<String> messages() {
        return store.recent(50);
    }
}
