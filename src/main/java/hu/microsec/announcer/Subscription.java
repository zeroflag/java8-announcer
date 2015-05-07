package org.announcer;

import java.util.function.Consumer;

public class Subscription {
    private final Class eventClass;
    private final Consumer action;

    public <T> Subscription(Class<T> eventClass, Consumer<T> action) {
        this.eventClass = eventClass;
        this.action = action;
    }

    public void deliver(Object event) {
        if (hasSubscribedTo(event))
            action.accept(event);
    }

    private boolean hasSubscribedTo(Object event) {
        return eventClass.isInstance(event);
    }
}
