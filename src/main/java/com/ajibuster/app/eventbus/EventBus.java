package com.ajibuster.app.eventbus;

import java.util.LinkedList;
import java.util.List;

public class EventBus {

  public interface Event {} // marker

  public interface Subscriber {
    public void onEvent(Event event);
  }
  
  private final List<Subscriber> mSubscribers = new LinkedList<Subscriber>();
  
  public void publish(Event event) {
    for (Subscriber subscriber : mSubscribers) {
        subscriber.onEvent(event);
    }
  }
  
  public void subscribe(Subscriber subscriber) {
    mSubscribers.add(subscriber);
  }

  public void unsubscribe(Subscriber subscriber) {
    mSubscribers.remove(subscriber);
  }
  
}
