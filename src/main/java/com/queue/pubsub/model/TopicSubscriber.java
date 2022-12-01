package com.queue.pubsub.model;

import com.queue.pubsub.interfaces.Subscriber;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
@Data
public class TopicSubscriber {

    private final AtomicInteger offset;

    private final Subscriber subscriber;

    public TopicSubscriber(final Subscriber subscriber) {
        this.subscriber = subscriber;
        this.offset = new AtomicInteger(0);
    }
}
