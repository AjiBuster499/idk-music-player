package com.ajibuster.app.eventbus.events;

import com.ajibuster.app.eventbus.Event;

class ForwardEvent implements Event {
  private String value = "Forward";

  public String getValue () {
    return this.value;
  }
}
