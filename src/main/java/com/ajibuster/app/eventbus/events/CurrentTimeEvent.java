package com.ajibuster.app.eventbus.events;

import com.ajibuster.app.eventbus.Event;

public class CurrentTimeEvent implements Event {
  private String value = "CurrentTime";

  private double timePercentage;

  public CurrentTimeEvent (double timePercentage) {
    this.timePercentage = timePercentage;
  }

  public String getValue () {
    return this.value;
  }

  public double getTimePercentage () {
    return this.timePercentage;
  }
}
