package com.ajibuster.app;

import java.nio.file.Paths;

// JavaFX Imports
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Application
 *
 */
public class App extends Application {
  // Global Items
  TextField songField;
  
  BorderPane bPane;
  MenuBar menuBar;
  Menu menuFile;
  MenuItem play, pause, switchSong, exit;
  
  Stage window;
  Scene scene;

  MediaHandler mh;


  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    window = primaryStage;
    window.setTitle("Music Player");
    String fileName = "Tactics.mp3";
    String filePath = Paths.get(fileName).toUri().toString();
    mh = new MediaHandler(filePath);

    generateUI();

    window.setScene(scene);
    window.show();

  }

  private void closeProgram() {
    // Runs on Program Close
    window.close();
  }

  /*********************************
  * generateUI()                   *
  * Sets up UI for Application     *
  * Return Value: TBD              *
  *********************************/
  private void generateUI() {
    this.bPane = new BorderPane();
    this.menuBar = new MenuBar();
    this.menuFile = new Menu("File");

    // menuFile Items
    this.play = new MenuItem("Play");
    this.pause = new MenuItem("Pause");
    this.switchSong = new MenuItem ("Change Song");
    this.exit = new MenuItem("Exit");

    // Text Field Creation
    this.songField = new TextField();


    // Define Actions for Menus
    this.exit.setOnAction(e -> {
      closeProgram();
    });
    
    play.setOnAction(e -> mh.playMusic());
    pause.setOnAction(e -> mh.pauseMusic());

    this.switchSong.setOnAction(e -> {
      OpenWindow.display("Choose a Song", this.songField);
    });

    this.songField.setOnAction(e -> {
      this.mh.pauseMusic();
      this.mh = MediaHandler.switchSong(songField.getText());
      OpenWindow.hide();
      this.mh.playMusic();
    });
    // Push MenuItems to Menus
    menuFile.getItems().addAll(this.play, this.pause, this.switchSong, new SeparatorMenuItem(), this.exit);


    // Push Menus to MenuBar
    this.menuBar.getMenus().addAll(this.menuFile);

    // Create Scene
    this.scene = new Scene(bPane, 300, 250);
    this.bPane.setTop(this.menuBar);
  }
}
