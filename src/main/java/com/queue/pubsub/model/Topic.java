package com.queue.pubsub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class Topic {

    private final String id;

    private final String name;

    private final List<Message> messages;

    private final List<TopicSubscriber> topicSubscribers;

    public Topic(final String id,
                 final String name) {
        this.id = id;
        this.name = name;
        this.messages = new ArrayList<>();
        this.topicSubscribers = new ArrayList<>();
    }

    public synchronized void addMessage(@NonNull final Message message) {
        messages.add(message);
    }

    public void addSubscriber(@NonNull final TopicSubscriber topicSubscriber) {
        topicSubscribers.add(topicSubscriber);
    }
}
