package com.ajibuster.app.eventbus.events;

import com.ajibuster.app.eventbus.Event;

public class RewindEvent implements Event {
  private String value = "Rewind";

  public String getValue () {
    return this.value;
  }
}
