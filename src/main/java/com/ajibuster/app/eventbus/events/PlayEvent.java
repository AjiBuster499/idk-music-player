package com.ajibuster.app.eventbus.events;

import com.ajibuster.app.eventbus.Event;

public class PlayEvent implements Event {
  private String value = "Play";

  public String getValue () {
    return this.value;
  }
}
