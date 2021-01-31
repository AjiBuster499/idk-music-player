package com.ajibuster.app.eventbus.events;

import com.ajibuster.app.eventbus.Event;

public class SeekTimeEvent implements Event {

  private String value = "SeekTimeEvent";

  private double timePercentage;

  public SeekTimeEvent (double timePercentage) {
    this.timePercentage = timePercentage;
  }

  public double getTimePercentage () {
    return this.timePercentage;
  }

  public String getValue () {
    return this.value;
  }
  
}
