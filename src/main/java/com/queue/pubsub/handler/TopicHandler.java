package com.queue.pubsub.handler;

import com.queue.pubsub.model.Topic;
import com.queue.pubsub.model.TopicSubscriber;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public class TopicHandler {

    private final Topic topic;

    private final Map<String, SubscriberWorker> subscriberWorkers;

    public TopicHandler(@NonNull final Topic topic) {
        this.topic = topic;
        this.subscriberWorkers = new HashMap<>();
    }

    public void publish() {
        for (TopicSubscriber subscriber : topic.getTopicSubscribers()) {
            publishMessageToSubscriber(subscriber);
        }
    }

    public void publishMessageToSubscriber(TopicSubscriber topicSubscriber) {
        final String subscriberId = topicSubscriber.getSubscriber().getId();

        // Lazy creation
        if (!subscriberWorkers.containsKey(subscriberId)) {
            final SubscriberWorker subscriberWorker = new SubscriberWorker(topic, topicSubscriber);
            subscriberWorkers.put(subscriberId, subscriberWorker);
            new Thread(subscriberWorker).start();
        }

        final SubscriberWorker subscriberWorker = subscriberWorkers.get(subscriberId);
        subscriberWorker.wakeUpIfNeeded();
    }
}
