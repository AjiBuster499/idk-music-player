package com.ajibuster.app.eventbus.events;

import com.ajibuster.app.eventbus.Event;

public class CurrentTimeEvent implements Event {
  private String value = "Current Time";

  public String getValue () {
    return this.value;
  }
  
}
