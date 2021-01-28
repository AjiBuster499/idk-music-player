package com.ajibuster.app.eventbus.events;

import com.ajibuster.app.eventbus.Event;

public class StopEvent implements Event {
  private String value = "Stop";

  public String getValue () {
    return this.value;
  }
}
