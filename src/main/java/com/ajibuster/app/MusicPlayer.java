package com.ajibuster.app;

import com.ajibuster.app.view.*;
import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.model.*;

import javafx.scene.layout.BorderPane;

public class MusicPlayer extends BorderPane {

  private TopMenu topMenu;
  private BottomPane bottomPane;
  private LeftPane leftPane;
  private EventBus eventBus;

  private MediaHandler mh;

  public MusicPlayer () {
    this.eventBus = new EventBus();
    this.topMenu = new TopMenu(this);
    this.bottomPane = new BottomPane(this, eventBus);
    this.leftPane = new LeftPane();

    this.mh = new MediaHandler();

    this.setTop(this.topMenu);
    this.setLeft(this.leftPane);
    this.setBottom(this.bottomPane);
  }

  public MediaHandler getMediaHandler () {
    return this.mh;
  }

  public void setMediaHandler (MediaHandler mh) {
    this.mh = mh;
  }
  
}
