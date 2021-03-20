package com.ajibuster.app.view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LeftPane extends VBox {
  // #3 Display Playlist in GUI
  public LeftPane () {
    Label test = new Label("This is a Test");
    setId("leftPane");

    this.getChildren().add(test);
  }
  
}
