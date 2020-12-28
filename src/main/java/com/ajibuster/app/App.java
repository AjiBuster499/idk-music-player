package com.ajibuster.app;

// JavaFX Imports
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Application
 *
 */
public class App extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("IDK Music Player");

    MusicPlayer mp = new MusicPlayer();

    Scene scene = new Scene(mp, 800, 800);

    primaryStage.setScene(scene);
    primaryStage.show();
  }

}
