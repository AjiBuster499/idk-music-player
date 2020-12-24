package com.ajibuster.app;

import javafx.scene.layout.BorderPane;

public class MusicPlayer extends BorderPane {

  private TopMenu topMenu;
  private BottomPane bottomPane;
  private LeftPane leftPane;
  // private CenterPane albumWindow;

  private MediaHandler mh;

  public MusicPlayer () {
    this.topMenu = new TopMenu(this);
    this.bottomPane = new BottomPane(this);
    this.leftPane = new LeftPane();
    this.albumWindow = new CenterPane();

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
