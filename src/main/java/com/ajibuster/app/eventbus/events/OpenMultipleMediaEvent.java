package com.ajibuster.app.eventbus.events;

import com.ajibuster.app.eventbus.Event;

public class OpenMultipleMediaEvent implements Event {

  private String value = "OpenMultipleMedia";

  public String getValue () {
    return this.value;
  }
  
}
