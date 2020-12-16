package com.ajibuster.app;

// JavaFX Imports
import javafx.application.Application;
import javafx.concurrent.Task;
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

  protected Task<Void> playDuration;

  protected Stage window;
  protected Scene scene;

  protected MediaHandler mh;

  public static void main (String[] args) {
    launch(args);
  }

  @Override
  public void start (Stage primaryStage) throws Exception {
    window = primaryStage;
    window.setTitle("IDK Music Player");

    generateUI();
    this.playDuration = updateProgressBar();
    playTime.progressProperty().bind(playDuration.progressProperty());

    window.setScene(scene);
    window.show();

    new Thread(this.playDuration).start();
  }

  private void closeProgram () {
    // Runs on Program Close
    window.close();
  }

  /*********************************
  * generateUI()                   *
  * Sets up UI for Application     *
  * Return Value: None             *
  *********************************/
  private void generateUI () {
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

    // Define Actions for Items
    this.exit.setOnAction(e -> {
      closeProgram();
    });
    // THE THREAD SHALL SCARE ME NOT
    
    this.play.setOnAction(e -> {
      if (mh != null) {
        mh.playMusic();
      }
    });
    this.pause.setOnAction(e -> {
      if (mh != null) {
        mh.pauseMusic();
      }
    });
    this.stop.setOnAction(e -> {
      if (mh != null) {
        mh.stopMusic();
      }
    });

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

  /* updateProgressBar()
   * Return Value: TBD
   * Runs a task to constantly update
   * ProgressBar (the time of the file) 
   */
  private Task<Void> updateProgressBar () {
    // So Basically
    // Run what I was timing here
    // Don't be afraid of Threads
    // Tasks run on the same thread as the GUI, but run in the background
    // Incremement the progress by 1 every 1000ms, up to a max of media.duration
    return new Task<Void>(){

      @Override
      protected Void call() throws Exception {
        final double max = mh.getPlayer().getMedia().getDuration().toSeconds();
        double currentTime = 0;
        while (currentTime <= max) {
          currentTime++;
          updateProgress(currentTime, max);
          updateMessage("Time" + currentTime);  

          // Sleep for 1000ms to avoid 1-shotting the bar
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        
        return null;
      }
    };
  
  }
}
