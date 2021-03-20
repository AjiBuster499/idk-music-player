package com.ajibuster.app;

import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.model.MediaHandler;

// JavaFX Imports
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
  private MusicPlayer mp;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("IDK Music Player");
    
    EventBus eventBus = new EventBus();
    MediaHandler mediaHandler = new MediaHandler(eventBus);
    this.mp = new MusicPlayer(mediaHandler, eventBus);

    Scene scene = new Scene(this.mp, 800, 800);

    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public MusicPlayer getRoot() {
    return this.mp;
  }

}
