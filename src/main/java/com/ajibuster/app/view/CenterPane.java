package com.ajibuster.app.view;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class CenterPane extends StackPane {
  // Center Pane holds album art.

  public CenterPane () {
    // Blank Pane for first opening
  }

  public void addImageView (ImageView iv) {
    // Avoid painting over other album covers.
    this.getChildren().clear();
    iv.setFitHeight(this.getScene().getHeight() * 0.8);
    iv.setFitWidth(this.getScene().getWidth() * 0.8);
    // Smoothe
    iv.setSmooth(true);
    this.getChildren().add(iv);
  }
  
}
