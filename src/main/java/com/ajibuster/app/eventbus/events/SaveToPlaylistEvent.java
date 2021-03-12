package com.ajibuster.app.eventbus.events;

import com.ajibuster.app.eventbus.Event;

public class SaveToPlaylistEvent implements Event {
    private String value = "SaveToPlaylist";

    public String getValue () {
      return this.value;
    }
}
