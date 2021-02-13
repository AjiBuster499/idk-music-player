package com.ajibuster.app.eventbus.events;

import com.ajibuster.app.eventbus.Event;

public class VolumeChangedEvent implements Event {
  private String value = "VolumeChanged";
  private double vol;

  public VolumeChangedEvent (double vol) {
    this.vol = vol;
  }

  // Future Support for specific vol changing
  public double getVol() {
    return this.vol;
  }
  
  public String getValue() {
    return this.value;
  }
}
