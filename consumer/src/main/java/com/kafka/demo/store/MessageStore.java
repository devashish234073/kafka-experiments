package com.kafka.demo.store;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class MessageStore {
    private final ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
    private final AtomicLong totalReceived = new AtomicLong(0);
    private final AtomicLong lastSequence = new AtomicLong(-1);
    private final AtomicLong lostMessages = new AtomicLong(0);

    public void add(String msg) {
        deque.addFirst(msg);
        if (deque.size() > 1000) {
            deque.removeLast();
        }
        totalReceived.incrementAndGet();

        if (msg.startsWith("Seq-")) {
            try {
                long currentSeq = Long.parseLong(msg.substring(4));
                long last = lastSequence.get();
                if (last != -1 && currentSeq > last + 1) {
                    long gap = currentSeq - last - 1;
                    lostMessages.addAndGet(gap);
                    System.err.println("GAP DETECTED! Missing " + gap + " messages. Last: " + last + ", Current: " + currentSeq);
                }
                if (currentSeq > last) {
                    lastSequence.set(currentSeq);
                }
            } catch (NumberFormatException e) {
                // ignore non-sequence messages
            }
        }
    }

    public List<String> recent(int limit) {
        return deque.stream().limit(limit).collect(Collectors.toList());
    }

    public long getTotalReceived() {
        return totalReceived.get();
    }

    public long getLostMessages() {
        return lostMessages.get();
    }
}
