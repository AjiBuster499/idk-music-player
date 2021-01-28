package com.ajibuster.app.eventbus.events;

import com.ajibuster.app.eventbus.Event;

public class ForwardEvent implements Event {
  private String value = "Forward";

  public String getValue () {
    return this.value;
  }
}
