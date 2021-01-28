package com.ajibuster.app.eventbus.events;

import com.ajibuster.app.eventbus.Event;

public class PauseEvent implements Event {
  private String value = "Pause";
  
  public String getValue () {
    return this.value;
  }
}
