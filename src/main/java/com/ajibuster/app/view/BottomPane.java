package com.ajibuster.app.view;

import com.ajibuster.app.MusicPlayer;
import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.eventbus.MediaEvent;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BottomPane extends VBox {
  
  private HBox bottomButtons;
  private Button play, pause, stop;
  private MusicPlayer mp;
  private EventBus eventBus;

  public BottomPane (MusicPlayer mp, EventBus eventBus) {
    this.bottomButtons = new HBox();
    this.eventBus = eventBus;

    this.mp = mp;

    this.play = new Button("Play");
    this.pause = new Button("Pause");
    this.stop = new Button("Stop");

    // this.play.setOnAction(this::handlePlay);
    // this.pause.setOnAction(this::handlePause);
    // this.stop.setOnAction(this::handleStop);

    this.bottomButtons.getChildren().addAll(this.play, this.pause, this.stop);
    this.getChildren().addAll(this.bottomButtons);

  }

  public void handle(MediaEvent event) {
    switch (event.getEventType()) {
      case PLAY:
        this.mp.getMediaHandler().playMusic();
        break;
      case FORWARD:
        break;
      case PAUSE:
        this.mp.getMediaHandler().pauseMusic();
        break;
      case REWIND:
        break;
      case STOP:
        this.mp.getMediaHandler().stopMusic();
        break;
      default:
        break;
    }
  }

  private void handlePlay(ActionEvent aEvent) {
    
  }
}
