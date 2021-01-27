package com.ajibuster.app.view;

import com.ajibuster.app.MusicPlayer;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BottomPane extends VBox {
  
  private HBox bottomButtons;
  private Button play, pause, stop;
  private MusicPlayer mp;

  public BottomPane (MusicPlayer mp) {
    this.bottomButtons = new HBox();

    this.mp = mp;

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
    // TEST: EventBus implementation
    this.mp.getMediaHandler().playMusic();
  }

  private void handlePause (ActionEvent aEvent) {
    this.mp.getMediaHandler().pauseMusic();
  }

  private void handleStop (ActionEvent aEvent) {
    this.mp.getMediaHandler().stopMusic();
  }
}
