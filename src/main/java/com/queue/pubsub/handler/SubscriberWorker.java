package com.queue.pubsub.handler;

import com.queue.pubsub.model.Message;
import com.queue.pubsub.model.Topic;
import com.queue.pubsub.model.TopicSubscriber;
import lombok.SneakyThrows;

public class SubscriberWorker implements Runnable {

    private final Topic topic;

    private final TopicSubscriber topicSubscriber;

    public SubscriberWorker(final Topic topic,
                            final TopicSubscriber topicSubscriber) {
        this.topic = topic;
        this.topicSubscriber = topicSubscriber;
    }


    @Override
    @SneakyThrows
    public void run() {
        synchronized (topicSubscriber) {
            do {
                int currOffset = topicSubscriber.getOffset().get();
                while(currOffset >= topic.getMessages().size()) {
                    topicSubscriber.wait();
                }

                Message message = topic.getMessages().get(currOffset);
                topicSubscriber.getSubscriber().consume(message);

                // First compare offset to validate if it wasn't updated by any other thread or wasn't reset then increment
                topicSubscriber.getOffset().compareAndSet(currOffset, currOffset + 1);

            } while (true);
        }
    }

    public synchronized void wakeUpIfNeeded() {
        synchronized (topicSubscriber) {
            topicSubscriber.notify();
        }
    }
}
