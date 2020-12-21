package com.ajibuster.app;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BottomPane extends VBox {
  
  private HBox bottomButtons;
  private Button play, pause, stop;
  private MusicPlayer musicPlayer;

  public BottomPane (MusicPlayer mp) {
    this.bottomButtons = new HBox();

    this.musicPlayer = mp;

    this.play = new Button("Play");
    this.pause = new Button("Pause");
    this.stop = new Button("Stop");

    this.play.setOnAction(this::handlePlay);
    this.pause.setOnAction(this::handlePause);
    this.stop.setOnAction(this::handleStop);

    this.bottomButtons.getChildren().addAll(this.play, this.pause, this.stop);
    this.getChildren().addAll(this.bottomButtons);

  }

  private void handlePlay (ActionEvent aEvent) {

  }

  private void handlePause (ActionEvent aEvent) {

  }

  private void handleStop (ActionEvent aEvent) {

  }
}
