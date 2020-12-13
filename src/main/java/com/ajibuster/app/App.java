package com.ajibuster.app;

import java.nio.file.Paths;
import java.util.Scanner;

// JavaFX Imports
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Application
 *
 */
public class App extends Application {
  // Global Items
  Button play, closeWindow, pause, switchSong;
  TextField songField;
  Stage window;
  MediaHandler mh;
  Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    window = primaryStage;
    window.setTitle("Music Player");
    String fileName = scanner.nextLine();
    String filePath = Paths.get(fileName).toUri().toString();
    mh = new MediaHandler(filePath);

    HBox topMenu = new HBox();
    HBox centerMenu = new HBox();
    BorderPane bPane = new BorderPane();
    bPane.setTop(topMenu);
    bPane.setCenter(centerMenu);
    centerMenu.setSpacing(10);

    // Button Creation
    closeWindow = new Button("Close Window");
    play = new Button("Play");
    pause = new Button("Pause");
    switchSong = new Button("Select Song");

    // Text Field Creation
    songField = new TextField();

    // Close Windows
    window.setOnCloseRequest(e -> closeProgram());
    closeWindow.setOnAction(e -> closeProgram());

    // Button Actions
    play.setOnAction(e -> mh.playMusic());
    pause.setOnAction(e -> mh.pauseMusic());
    switchSong.setOnAction(e -> {
      mh = MediaHandler.switchSong(songField.getText());
    });

    topMenu.getChildren().addAll(play, pause, closeWindow);
    centerMenu.getChildren().addAll(songField, switchSong);

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
