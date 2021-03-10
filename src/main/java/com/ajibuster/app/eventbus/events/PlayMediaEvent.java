package com.ajibuster.app.eventbus.events;

import com.ajibuster.app.eventbus.Event;

public class PlayMediaEvent implements Event {
  private String value = "PlayMedia";

  public String getValue () {
    return this.value;
  }
}
