package com.ajibuster.app.eventbus.events;

import com.ajibuster.app.eventbus.Event;

public class SkipMediaEvent implements Event {
  private String value = "SkipMedia";

  public String getValue () {
    return this.value;
  }
  
}
