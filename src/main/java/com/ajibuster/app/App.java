package com.ajibuster.app;
// Imports
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * Application
 *
 */
public class App extends Application{
  // Global Items
  Button play;

  public static void main( String[] args ) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
      primaryStage.setTitle("Music Player");
      StackPane layout = new StackPane();
      Scene scene = new Scene(layout, 300, 250);

      play = new Button("Play");
      play.setOnAction(e -> System.out.println("play"));

      layout.getChildren().add(play);

      primaryStage.setScene(scene);
      primaryStage.show();
      
  }
}
