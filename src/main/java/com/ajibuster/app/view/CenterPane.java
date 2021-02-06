package com.ajibuster.app.view;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class CenterPane extends StackPane {
  // Center Pane holds album art.

  public CenterPane () {
    // Blank Pane for first opening
    this.setOnMouseClicked(e -> {
      System.out.println("You Clicked On Center");
    });
  }

  public void addImageView (ImageView iv) {
    this.getChildren().clear();
    this.getChildren().add(iv);
  }
  
}
