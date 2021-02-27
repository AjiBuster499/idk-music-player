package com.ajibuster.app.eventbus.events;

import com.ajibuster.app.eventbus.Event;

import javafx.util.Duration;

public class CurrentTimeEvent implements Event {
  private String value = "CurrentTime";

  private double timePercentage;
  private Duration timeDuration;

  public CurrentTimeEvent (double timePercentage, Duration timeDuration) {
    this.timePercentage = timePercentage;
    this.timeDuration = timeDuration;
  }

  public String getValue () {
    return this.value;
  }

  public double getTimePercentage () {
    return this.timePercentage;
  }

  public Duration getTimeDuration () {
    return this.timeDuration;
  }
}
