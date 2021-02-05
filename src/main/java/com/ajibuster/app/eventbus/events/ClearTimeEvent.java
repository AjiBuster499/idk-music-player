package com.ajibuster.app.eventbus.events;

import com.ajibuster.app.eventbus.Event;

public class ClearTimeEvent implements Event {
  private String value = "ClearTime";

  public String getValue() {
    return this.value;
  }
  
}
