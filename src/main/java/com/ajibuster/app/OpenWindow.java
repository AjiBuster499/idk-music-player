package com.ajibuster.app;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @deprecated
 * I don't know why I still have this in here.
 * FileWindow handles opening Files.
 * @author ajibuster499
 * @see FileWindow.java
 */
public class OpenWindow {
  static Stage window;

  public static void display (String title, Node item) {
    window = new Stage();

    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle(title);
    window.setMinWidth(250);

    VBox layout = new VBox(10);

    layout.getChildren().addAll(item);
    layout.setAlignment(Pos.CENTER);
    
    Scene scene = new Scene(layout);
    
    window.setScene(scene);
    window.showAndWait();
  }
}
