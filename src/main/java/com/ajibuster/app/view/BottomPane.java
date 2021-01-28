package com.ajibuster.app.view;

import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.eventbus.events.*;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BottomPane extends VBox {
  
  private HBox bottomButtons;
  private Button play, pause, stop;
  private EventBus eventBus;

  public BottomPane (EventBus eventBus) {
    this.bottomButtons = new HBox();
    this.eventBus = eventBus;

    this.play = new Button("Play");
    this.pause = new Button("Pause");
    this.stop = new Button("Stop");

    this.play.setOnAction(this::handlePlay);
    this.pause.setOnAction(this::handlePause);
    this.stop.setOnAction(this::handleStop);

    this.bottomButtons.getChildren().addAll(this.play, this.pause, this.stop);
    this.getChildren().addAll(this.bottomButtons);

  }

  private void handlePlay(ActionEvent aEvent) {
    this.eventBus.emit(new PlayEvent());
  }

  private void handlePause (ActionEvent aEvent) {
    this.eventBus.emit(new PauseEvent());
  }

  private void handleStop (ActionEvent aEvent) {
    this.eventBus.emit(new StopEvent());
  }
}
