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

  /******************************************************************
   * generateUI() * Sets up UI for Application * Return Value: None *
   *****************************************************************/
  // private void generateUI() {

  //   // Initialize Others
  //   albumPicture = new ImageView();
  //   timeBar = new Slider(0, isMediaHandlerAlive() ? mh.duration : 10, 0);
  //   sr = new SliderRunnable(timeBar, isMediaHandlerAlive() ? mh.duration : 10);

  //   // Initialize Buttons
  //   forward = new Button("Forward");
  //   back = new Button("Back");

  //   // Push UI Items to Components
  //   bottomPane.getChildren().addAll(timeBar, bottomButtons);
  //   centerPane.getChildren().add(albumPicture);
  // }

  // private void closeProgram() {
  //   System.out.println("Stopping all Threads");
  //   sr.requestStop();
  //   // Runs on Program Close
  //   window.close();
  // }
}
