package com.ajibuster.app.eventbus.events;

import com.ajibuster.app.eventbus.Event;

public class OpenEvent implements Event {

  private String value = "Open";

  public String getValue () {
    return this.value;
  }
  
}
