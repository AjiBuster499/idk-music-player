package com.ajibuster.app.eventbus.events;

import com.ajibuster.app.eventbus.Event;

public class StopMediaEvent implements Event {
  private String value = "StopMedia";

  public String getValue () {
    return this.value;
  }
}
