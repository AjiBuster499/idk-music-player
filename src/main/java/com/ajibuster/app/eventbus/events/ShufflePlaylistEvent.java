package com.ajibuster.app.eventbus.events;

import com.ajibuster.app.eventbus.Event;
import com.ajibuster.app.model.media.ShuffleStatus;

public class ShufflePlaylistEvent implements Event {
  private String value = "ShufflePlaylist";
  private ShuffleStatus status;

  public ShufflePlaylistEvent (ShuffleStatus status) {
    this.status = status;
  }

  public ShuffleStatus getStatus () {
    return this.status;
  }

  public String getValue () {
    return this.value;
  }
  
}
