package com.ajibuster.app;

import javafx.scene.layout.BorderPane;

public class MusicPlayer extends BorderPane {

  private TopMenu topMenu;
  private BottomPane bottomPane;
  private LeftPane leftPane;

  public MusicPlayer () {
    this.topMenu = new TopMenu();
    this.bottomPane = new BottomPane(this);
    this.leftPane = new LeftPane();


    this.setTop(this.topMenu);
    this.setLeft(this.leftPane);
    this.setBottom(this.bottomPane);
  }
  
}
