package com.ajibuster.app.eventbus.events;

import com.ajibuster.app.eventbus.Event;

public class OpenPlaylistEvent implements Event {

  private String value = "OpenPlaylist";

  public String getValue () {
    return this.value;
  }
  
}
