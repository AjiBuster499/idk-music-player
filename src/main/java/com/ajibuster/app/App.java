package com.ajibuster.app;

// Java Imports
import java.util.Scanner;

// JavaFX Imports
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Application
 *
 */
public class App extends Application {
  // Global Items
  Button play, closeWindow, pause;
  Stage window;
  MediaHandler mh;
  Scanner input = new Scanner(System.in);

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    window = primaryStage;
    window.setTitle("Music Player");
    mh = new MediaHandler();

    HBox topMenu = new HBox();
    BorderPane bPane = new BorderPane();
    bPane.setTop(topMenu);

    // Button Creation
    closeWindow = new Button("Close Window");
    play = new Button("Play");
    pause = new Button("Pause");

    // Close Windows
    window.setOnCloseRequest(e -> closeProgram());
    closeWindow.setOnAction(e -> closeProgram());

    // Button Actions
    play.setOnAction(e -> mh.playMusic());
    pause.setOnAction(e -> mh.pauseMusic());

    topMenu.getChildren().addAll(play, pause, closeWindow);

    Scene scene = new Scene(bPane, 300, 250);
    window.setScene(scene);
    window.show();

  }

  private void closeProgram() {
    // Runs on Program Close
    System.out.println("Closed Successfully");
    window.close();
  }
}
