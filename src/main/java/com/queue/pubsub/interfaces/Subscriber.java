package com.queue.pubsub.interfaces;

import com.queue.pubsub.model.Message;

public interface Subscriber {

    public String getId();

    public void consume(Message message);
}
