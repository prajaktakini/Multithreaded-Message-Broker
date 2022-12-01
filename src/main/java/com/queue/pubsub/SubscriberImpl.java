package com.queue.pubsub;

import com.queue.pubsub.interfaces.Subscriber;
import com.queue.pubsub.model.Message;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SubscriberImpl implements Subscriber {

    private final String id;

    private final int sleepTimeInMillies;

    public SubscriberImpl(final String id,
                          final int sleepTimeInMillies) {
        this.id = id;
        this.sleepTimeInMillies = sleepTimeInMillies;
    }

    @Override
    public String getId() {
        return id;
    }

    @SneakyThrows
    @Override
    public void consume(final Message message) {
        log.info("Subscriber {} started consuming message {}", id, message);
        Thread.sleep(sleepTimeInMillies);
        log.info("Subscriber {} processed messaged {}", id, message);
    }
}
