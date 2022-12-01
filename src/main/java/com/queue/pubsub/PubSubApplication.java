package com.queue.pubsub;

import com.queue.pubsub.interfaces.Queue;
import com.queue.pubsub.model.Message;
import com.queue.pubsub.model.Topic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PubSubApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(PubSubApplication.class, args);

		final Queue queue = new Queue();
		final Topic topic1 = queue.createTopic("topic1");
		final Topic topic2 = queue.createTopic("topic2");

		final SubscriberImpl subscriber1 = new SubscriberImpl("sub1", 10000);
		final SubscriberImpl subscriber2 = new SubscriberImpl("sub2", 10000);
		queue.subscribe(subscriber1, topic1);
		queue.subscribe(subscriber2, topic1);

		final SubscriberImpl subscriber3 = new SubscriberImpl("sub3", 5000);
		queue.subscribe(subscriber3, topic2);

		queue.publish(topic1, new Message("m1"));
		queue.publish(topic1, new Message("m2"));

		queue.publish(topic2, new Message("m3"));

		Thread.sleep(15000);
		queue.publish(topic2, new Message("m4"));
		queue.publish(topic1, new Message("m5"));

		queue.resetOffset(subscriber1, topic1, 0);

	}

}
