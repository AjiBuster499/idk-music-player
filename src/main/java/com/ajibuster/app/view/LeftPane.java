package com.ajibuster.app.view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LeftPane extends VBox {

  public LeftPane () {
    Label test = new Label("This is a Test");

    this.getChildren().add(test);

    this.setOnMouseClicked(e -> {
      System.out.println("You Clicked On Left");
    });
  }
  
}
