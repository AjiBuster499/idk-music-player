package com.ajibuster.app.eventbus.events;

import com.ajibuster.app.eventbus.Event;

public class VolumeDownEvent implements Event {

  private String value = "Volume Down";

  public String getValue () {
    return this.value;
  }
  
}
