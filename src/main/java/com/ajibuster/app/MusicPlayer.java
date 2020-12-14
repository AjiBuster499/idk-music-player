package com.ajibuster.app;

import java.nio.file.Paths;

// JavaFX Imports
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.Scene;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Application
 *
 */
public class MusicPlayer extends Application {
  // Global Items  
  BorderPane bPane;
  GridPane gp;
  HBox bottomPane;
  MenuBar menuBar;
  Menu menuFile;
  MenuItem play, pause, open, exit;
  VBox leftSidePane;
  
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
  * Return Value: None             *
  *********************************/
  private void generateUI() {
    this.bPane = new BorderPane();
    this.menuBar = new MenuBar();
    this.menuFile = new Menu("File");
    this.gp = new GridPane();
    this.bottomPane = new HBox();
    this.leftSidePane = new VBox();

    //Set up GridPane
    this.gp.setPadding(new Insets(10, 10, 10, 10));
    this.gp.setVgap(8);
    this.gp.setHgap(10);

    // Test Button
    Button test = new Button("Test!");
    test.setOnAction(e -> {
      System.out.println("Test Passed Failurely!");
    });

    // menuFile Items
    this.play = new MenuItem("Play");
    this.pause = new MenuItem("Pause");
    this.open = new MenuItem ("Change Song");
    this.exit = new MenuItem("Exit");

    // Define Actions for Menus
    this.exit.setOnAction(e -> {
      closeProgram();
    });
    
    play.setOnAction(e -> mh.playMusic());
    pause.setOnAction(e -> mh.pauseMusic());

    this.open.setOnAction(e -> {
      FileWindow fw = new FileWindow(this.mh);
      this.mh = fw.display("Open a File...");
    });

    // Push MenuItems to Menus
    this.menuFile.getItems().addAll(this.play, this.pause, this.open, new SeparatorMenuItem(), this.exit);

    // Push UI Items to GridPane
    GridPane.setConstraints(test, 0, 0);

    // Push Menus to MenuBar
    this.menuBar.getMenus().addAll(this.menuFile);

    // Create Scene
    this.scene = new Scene(bPane, 300, 250);
    this.bPane.setTop(this.menuBar);
    this.bPane.setCenter(this.gp);
    this.bPane.setLeft(this.leftSidePane);
    this.bPane.setBottom(this.bottomPane);
  }
}
