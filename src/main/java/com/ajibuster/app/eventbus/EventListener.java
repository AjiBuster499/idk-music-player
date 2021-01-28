package com.ajibuster.app.eventbus;

public interface EventListener<T extends Event> {

  void handle(T event);
  
}
