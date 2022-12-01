package com.queue.pubsub.interfaces;

import com.queue.pubsub.handler.TopicHandler;
import com.queue.pubsub.model.Message;
import com.queue.pubsub.model.Topic;
import com.queue.pubsub.model.TopicSubscriber;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class Queue {

    private final Map<String, TopicHandler> topicHandlers;

    public Queue() {
        this.topicHandlers = new HashMap<>();
    }

    public Topic createTopic(@NonNull final String topicName) {
        Topic topic = new Topic(UUID.randomUUID().toString(), topicName);
        TopicHandler topicHandler = new TopicHandler(topic);
        topicHandlers.put(topicName, topicHandler);
        log.info("Created topic {}", topicName);
        return topic;
    }

    public void subscribe(@NonNull final Subscriber subscriber, @NonNull final Topic topic) {
        TopicSubscriber topicSubscriber = new TopicSubscriber(subscriber);
        topic.addSubscriber(topicSubscriber);
        log.info("Subscriber {} subscribed to topic {}", subscriber.getId(), topic.getName());
    }

    public void publish(@NonNull final Topic topic,
                        @NonNull final Message message) {
        topic.addMessage(message);
        log.info("Message {} published to topic {}", message, topic.getName());
        new Thread(() -> topicHandlers.get(topic.getName()).publish()).start();
    }

    public void resetOffset(@NonNull final Subscriber subscriber, @NonNull final Topic topic, @NonNull Integer offset) {
        for (TopicSubscriber topicSubscriber : topic.getTopicSubscribers()) {
            if (topicSubscriber.getSubscriber().equals(subscriber)) {
                topicSubscriber.getOffset().set(offset);
                log.info("Subscriber {} reset offset to {}", subscriber.getId(), offset);
                new Thread(() -> topicHandlers.get(topic.getName()).publishMessageToSubscriber(topicSubscriber)).start();
                break;
            }
        }
    }


}
