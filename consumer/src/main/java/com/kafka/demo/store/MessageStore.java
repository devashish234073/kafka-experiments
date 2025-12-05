package com.kafka.demo.store;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

@Component
public class MessageStore {
    private final ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();

    public void add(String msg) {
        deque.addFirst(msg);
        if (deque.size() > 1000) {
            deque.removeLast();
        }
    }

    public List<String> recent(int limit) {
        return deque.stream().limit(limit).collect(Collectors.toList());
    }
}
