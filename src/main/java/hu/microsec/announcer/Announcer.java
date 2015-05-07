package org.announcer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * Announcer announcer = new Announcer();
 *
 * // simple lambda subscriptions
 * announcer.subscribe(String.class, str -> System.out.println("received string: " + str));
 * announcer.subscribe(Number.class, n -> System.out.println("received number: " + n));
 * announcer.subscribe(Integer.class, i -> System.out.println(i*2));
 *
 * // announce events
 * announcer.announce("test string");
 * announcer.announce(3);
 *
 *  // custom listener and event objects
 * MyListener listener = new MyListener();
 * announcer.subscribe(AnEvent.class, listener::onTestEvent);
 * announcer.announce(new AnEvent("some payload"));
 * announcer.announce(new ASubEvent("some payload"));
 *
 * // unsubscribe
 * Subscription subscription = announcer.subscribe(AnEvent.class, (evt) -> {})
 * announcer.unsubscribe(subscription);
 */
public class Announcer {
    private final List<Subscription> subscriptions = new CopyOnWriteArrayList<>();

    public <T> Subscription subscribe(Class<T> event, Consumer<T> action) {
        if (event == null)
            throw new NullPointerException("Event class cannot be null " + event);
        Subscription subscription = new Subscription(event, action);
        subscriptions.add(subscription);
        return subscription;
    }

    public void unsubscribe(Subscription subscription) {
        subscriptions.remove(subscription);
    }

    public void announce(Object event) {
        subscriptions.forEach(subscription -> subscription.deliver(event));
    }
}
