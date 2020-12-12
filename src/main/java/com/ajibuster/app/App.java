package com.ajibuster.app;

import java.util.ArrayList;

// Imports
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;



/**
 * Application
 *
 */
public class App extends Application {
  // Global Items
  Button play, closeWindow;
  Stage window;

  public static void main( String[] args ) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    window = primaryStage;
    window.setTitle("Music Player");

    StackPane layout = new StackPane();
    Scene scene = new Scene(layout, 300, 250);
    

    closeWindow = new Button("Close Window");
    play = new Button("Play");

    window.setOnCloseRequest(e -> closeProgram());
    closeWindow.setOnAction(e -> closeProgram());
    play.setOnAction(e -> MediaHandler.playMusic());
    
    layout.getChildren().add(play);
    layout.getChildren().add(closeWindow);

    window.setScene(scene);
    window.show();
    
  }

  private void closeProgram() {
    // Runs on Program Close

    window.close();
  }
}
