package com.ajibuster.app.eventbus.events;

import com.ajibuster.app.eventbus.Event;

public class CurrentTimeEvent implements Event {
  private String value = "CurrentTime";

  private double time;

  public CurrentTimeEvent (double time) {
    this.time = time;
  }

  public String getValue () {
    return this.value;
  }

  public double getTime () {
    return this.time;
  }
}
