package com.ajibuster.app.eventbus.events;

import com.ajibuster.app.eventbus.Event;
import com.ajibuster.app.model.RepeatStatus;

public class RepeatStatusChangeEvent implements Event {
  private String value = "RepeatStatusChanged";
  
  private RepeatStatus status;

  public RepeatStatusChangeEvent (RepeatStatus s) {
    this.status = s;
  }

  public RepeatStatus getStatus () {
    return this.status;
  }

  public String getValue () {
    return this.value;
  }
  
}
