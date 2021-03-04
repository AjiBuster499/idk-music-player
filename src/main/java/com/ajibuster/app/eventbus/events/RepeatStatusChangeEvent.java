package com.ajibuster.app.eventbus.events;

import com.ajibuster.app.eventbus.Event;
import com.ajibuster.app.model.Repeat;

public class RepeatStatusChangeEvent implements Event {
  private String value = "RepeatStatusChanged";
  
  private Repeat status;

  public RepeatStatusChangeEvent (Repeat s) {
    this.status = s;
  }

  public Repeat getStatus () {
    return this.status;
  }

  public String getValue () {
    return this.value;
  }
  
}
