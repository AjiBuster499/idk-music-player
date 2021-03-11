package com.ajibuster.app.eventbus.events;

import com.ajibuster.app.eventbus.Event;

import javafx.scene.image.ImageView;

public class LoadArtEvent implements Event {
  private String value = "LoadArt";
  private ImageView iv;

  public LoadArtEvent (ImageView iv) {
    this.iv = iv;
  }

  public String getValue () {
    return this.value;
  }

  public ImageView getImageView () {
    return this.iv;
  }
  
}
