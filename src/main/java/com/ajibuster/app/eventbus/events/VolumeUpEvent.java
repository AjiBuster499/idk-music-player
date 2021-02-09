package com.ajibuster.app.eventbus.events;

import com.ajibuster.app.eventbus.Event;

public class VolumeUpEvent implements Event {

  private String value = "Volume Up";

  public String getValue () {
    return this.value;
  }
  
}
