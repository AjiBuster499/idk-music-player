package com.ajibuster.app.eventbus.events;

import com.ajibuster.app.eventbus.Event;

public class PauseMediaEvent implements Event {
  private String value = "PauseMedia";
  
  public String getValue () {
    return this.value;
  }
}
