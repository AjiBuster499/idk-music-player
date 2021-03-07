package com.ajibuster.app.eventbus.events;

import com.ajibuster.app.eventbus.Event;

public class OpenMediaEvent implements Event {

  private String value = "OpenMedia";

  public String getValue () {
    return this.value;
  }
  
}
