https://travis-ci.org/zeroflag/java8-announcer.svg?branch=master


```java
 Announcer announcer = new Announcer();

 // simple lambda subscriptions
 announcer.subscribe(String.class, str -> System.out.println("received string: " + str));
 announcer.subscribe(Number.class, n -> System.out.println("received number: " + n));
 announcer.subscribe(Integer.class, i -> System.out.println(i*2));

 // announce events
 announcer.announce("test string");
 announcer.announce(3);

  // custom listener and event objects
 MyListener listener = new MyListener();
 announcer.subscribe(AnEvent.class, listener::onTestEvent);
 announcer.announce(new AnEvent("some payload"));
 announcer.announce(new ASubEvent("some payload"));

 // unsubscribe
 Subscription subscription = announcer.subscribe(AnEvent.class, (evt) -> {})
 announcer.unsubscribe(subscription);
```
