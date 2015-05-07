package org.announcer;

import org.announcer.Announcer;
import org.announcer.Subscription;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.util.function.Consumer;

import static org.hamcrest.core.Is.is;

public class AnnouncerTest {

    @Test
    public void test_notifies_single_listener() throws Exception {
        context.checking(new Expectations() {{
            oneOf(listener1).accept("event");
        }});
        announcer.subscribe(String.class, listener1::accept);
        announcer.announce("event");
    }

    @Test
    public void test_notifies_multiple_listeners() throws Exception {
        context.checking(new Expectations() {{
            oneOf(listener1).accept("event");
            oneOf(listener2).accept("event");
        }});
        announcer.subscribe(String.class, listener1::accept);
        announcer.subscribe(String.class, listener2::accept);
        announcer.announce("event");
    }

    @Test
    public void test_uninterested_listeners_are_not_notified() throws Exception {
        context.checking(new Expectations() {{
            oneOf(listener1).accept("event");
            never(listener3).accept(with(any(AnEvent.class)));
        }});
        announcer.subscribe(String.class, listener1::accept);
        announcer.subscribe(AnEvent.class, listener3::accept);
        announcer.announce("event");
    }

    @Test
    public void test_notifies_listeners_subsribed_to_subevents_and_baseevents() throws Exception {
        final ASubEvent event = new ASubEvent("test");
        context.checking(new Expectations() {{
            oneOf(listener3).accept(with(is(event)));
            oneOf(listener4).accept(with(is(event)));
        }});
        announcer.subscribe(AnEvent.class, listener3::accept);
        announcer.subscribe(ASubEvent.class, listener4::accept);
        announcer.announce(event);
    }

    @Test
    public void test_listener_subsribed_to_subevent_is_uninterested_to_base_event() throws Exception {
        context.checking(new Expectations() {{
            never(listener4).accept(with(any(ASubEvent.class)));
        }});
        announcer.subscribe(ASubEvent.class, listener3::accept);
        announcer.announce(new AnEvent("test"));
    }

    @Test
    public void test_unsubscribeded_listeners_are_not_notified() throws Exception {
        context.checking(new Expectations() {{
            exactly(2).of(listener1).accept("event");
            exactly(1).of(listener2).accept("event");
        }});
        Subscription subscription1 = announcer.subscribe(String.class, listener1::accept);
        Subscription subscription2 = announcer.subscribe(String.class, listener2::accept);
        announcer.announce("event");
        announcer.unsubscribe(subscription2);
        announcer.announce("event");
        announcer.unsubscribe(subscription1);
        announcer.announce("event");
    }

    public @Rule JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock Consumer<String> listener1;
    @Mock Consumer<String> listener2;
    @Mock Consumer<AnEvent> listener3;
    @Mock Consumer<ASubEvent> listener4;
    Announcer announcer = new Announcer();
}
