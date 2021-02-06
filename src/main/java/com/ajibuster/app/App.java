package com.ajibuster.app;

import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.model.MediaHandler;

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
    
    EventBus eventBus = new EventBus();
    MediaHandler mediaHandler = new MediaHandler(eventBus);
    MusicPlayer mp = new MusicPlayer(mediaHandler, eventBus);

    Scene scene = new Scene(mp, 800, 800);
    mp.getBottom().prefHeight(scene.getHeight() * 0.1);
    mp.getTop().prefHeight(scene.getHeight() * 0.1);
    mp.getLeft().prefWidth(scene.getWidth() * 0.2);
    mp.getCenter().prefWidth(scene.getWidth() * 0.8);
    mp.getCenter().prefHeight(scene.getHeight() * 0.85);

    primaryStage.setScene(scene);
    primaryStage.show();
  }

}
