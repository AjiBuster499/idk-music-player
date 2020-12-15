package com.ajibuster.app;

// JavaFX Imports
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.Scene;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Application
 *
 */
public class MusicPlayer extends Application {
  // Global Items  
  protected BorderPane bPane;
  protected StackPane centerPane;
  protected HBox bottomButtons;
  protected MenuBar menuBar;
  protected Menu menuFile;
  protected MenuItem open, exit;
  protected VBox leftSidePane, bottomPane;
  
  protected Button play, pause, stop, forward, back;
  protected ProgressBar playTime;
  protected ImageView albumPicture;

  protected Stage window;
  protected Scene scene;

  protected MediaHandler mh;


  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    window = primaryStage;
    window.setTitle("Music Player");

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
    // Initialize UI Components
    this.bPane = new BorderPane();
    this.menuBar = new MenuBar();
    this.menuFile = new Menu("File");
    this.centerPane = new StackPane();
    this.bottomButtons = new HBox();
    this.bottomPane = new VBox();
    this.leftSidePane = new VBox();

    // Initialize Others
    this.playTime = new ProgressBar();
    this.albumPicture = new ImageView();

    // Initialize Buttons
    this.play = new Button("Play");
    this.pause = new Button("Pause");
    this.stop = new Button("Stop");
    this.forward = new Button("Forward");
    this.back = new Button("Back");


    // menuFile Items
    this.open = new MenuItem ("Open File...");
    this.exit = new MenuItem("Exit");

    // Display Album Cover

    // Define Actions for Items
    this.exit.setOnAction(e -> {
      closeProgram();
    });
    
    this.play.setOnAction(e -> mh.playMusic());
    this.pause.setOnAction(e -> mh.pauseMusic());
    this.stop.setOnAction(e -> mh.stopMusic());

    this.open.setOnAction(e -> {
      if (this.mh == null) {
        this.mh = new MediaHandler();
      }
      FileWindow fw = new FileWindow(this.mh);
      this.mh = fw.display("Open a File...");
    });

    // Push MenuItems to Menus
    this.menuFile.getItems().addAll(this.open, new SeparatorMenuItem(), this.exit);

    // Push UI Items to Components
    this.bottomButtons.getChildren().addAll(this.play, this.pause, this.stop);
    this.bottomPane.getChildren().addAll(this.playTime, this.bottomButtons);
    this.centerPane.getChildren().add(albumPicture);

    // Push Menus to MenuBar
    this.menuBar.getMenus().addAll(this.menuFile);

    // Create Scene
    this.scene = new Scene(bPane, 800, 800);
    this.bPane.setTop(this.menuBar);
    this.bPane.setCenter(this.centerPane);
    this.bPane.setLeft(this.leftSidePane);
    this.bPane.setBottom(this.bottomPane);
  }
}
