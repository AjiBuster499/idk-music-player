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
import javafx.scene.Scene;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Application
 *
 */
public class App extends Application {
  // Global Items  
  protected BorderPane bPane;
  protected StackPane centerPane;
  protected HBox bottomButtons;
  protected MenuBar menuBar;
  protected Menu menuFile;
  protected MenuItem open, exit;
  protected VBox leftSidePane, bottomPane;
  
  protected Button play, pause, stop, forward, back;
  protected Slider timeBar;
  protected ImageView albumPicture;

  protected Stage window;
  protected Scene scene;

  protected SliderRunnable sr;

  protected MediaHandler mh;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    window = primaryStage;
    window.setTitle("IDK Music Player");

    generateUI();

    window.setScene(scene);
    window.setOnCloseRequest(e -> closeProgram());
    window.show();

    Thread th = new Thread(sr, "Slider-Thread");
    th.start();
  }

  /******************************************************************
   * generateUI() * Sets up UI for Application * Return Value: None *
   *****************************************************************/
  private void generateUI() {
    // Initialize UI Components
    bPane = new BorderPane();
    menuBar = new MenuBar();
    menuFile = new Menu("File");
    centerPane = new StackPane();
    bottomButtons = new HBox();
    bottomPane = new VBox();
    leftSidePane = new VBox();

    // Initialize Others
    albumPicture = new ImageView();
    timeBar = new Slider(0, isMediaHandlerAlive() ? mh.duration : 10, 0);
    sr = new SliderRunnable(timeBar, isMediaHandlerAlive() ? mh.duration : 10);

    // Initialize Buttons
    play = new Button("Play");
    pause = new Button("Pause");
    stop = new Button("Stop");
    forward = new Button("Forward");
    back = new Button("Back");

    // menuFile Items
    open = new MenuItem("Open File...");
    exit = new MenuItem("Exit");

    // Define Actions for Items
    exit.setOnAction(e -> {
      closeProgram();
    });

    play.setOnAction(e -> {
      if (isMediaHandlerAlive()) {
        mh.playMusic();
      }
    });
    pause.setOnAction(e -> {
      if (isMediaHandlerAlive()) {
        mh.pauseMusic();
      }
    });
    stop.setOnAction(e -> {
      if (isMediaHandlerAlive()) {
        mh.stopMusic();
      }
    });

    open.setOnAction(e -> {
      if (!isMediaHandlerAlive()) {
        mh = new MediaHandler();
      }
      FileWindow fw = new FileWindow(mh);
      mh = fw.display("Open a File...");
    });

    // Push MenuItems to Menus
    menuFile.getItems().addAll(open, new SeparatorMenuItem(), exit);

    // Push UI Items to Components
    bottomButtons.getChildren().addAll(play, pause, stop);
    bottomPane.getChildren().addAll(timeBar, bottomButtons);
    centerPane.getChildren().add(albumPicture);

    // Push Menus to MenuBar
    menuBar.getMenus().addAll(menuFile);

    // Create Scene
    scene = new Scene(bPane, 800, 800);
    bPane.setTop(menuBar);
    bPane.setCenter(centerPane);
    bPane.setLeft(leftSidePane);
    bPane.setBottom(bottomPane);
  }

  private void closeProgram() {
    System.out.println("Stopping all Threads");
    sr.requestStop();
    // Runs on Program Close
    window.close();
  }

  private boolean isMediaHandlerAlive() {
    if (this.mh == null) {
      return false;
    } else {
      return true;
    }
  }

  // Abandoned until Further Need
  // private void openFile () {
  //   FileWindow fw = new FileWindow(this.mh);
  //   this.mh = fw.display("Open a File...");
  // }
}
