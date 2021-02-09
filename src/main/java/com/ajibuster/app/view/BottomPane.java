package com.ajibuster.app.view;

import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.eventbus.events.*;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BottomPane extends VBox {
  
  private HBox bottomButtons;
  private Button play, pause, stop, forward, rewind, volumeUp, volumeDown;
  private SeekBar seekBar;
  private EventBus eventBus;

  public BottomPane (EventBus eventBus) {
    this.bottomButtons = new HBox();
    this.eventBus = eventBus;

    this.play = new Button("Play");
    this.pause = new Button("Pause");
    this.stop = new Button("Stop");
    this.forward = new Button("Forward");
    this.rewind = new Button("Rewind");
    this.volumeUp = new Button("+");
    this.volumeDown = new Button("-");
    this.seekBar = new SeekBar(this.eventBus);

    // TODO CLEANUP: Simplify this
    this.play.setOnAction(this::handlePlay);
    this.pause.setOnAction(this::handlePause);
    this.stop.setOnAction(this::handleStop);
    this.forward.setOnAction(this::handleForward);
    this.rewind.setOnAction(this::handleRewind);
    this.volumeUp.setOnAction(this::handleVolumeUp);
    this.volumeDown.setOnAction(this::handleVolumeDown);

    this.bottomButtons.getChildren().addAll(this.play, this.pause, this.stop, this.forward, this.rewind, this.volumeUp, this.volumeDown);
    this.getChildren().addAll(this.bottomButtons, this.seekBar);

  }

  private void handleForward (ActionEvent aEvent) {
    this.eventBus.emit(new ForwardEvent());
  }

  private void handlePlay (ActionEvent aEvent) {
    this.eventBus.emit(new PlayEvent());
  }

  private void handlePause (ActionEvent aEvent) {
    this.eventBus.emit(new PauseEvent());
  }

  private void handleRewind (ActionEvent aEvent) {
    this.eventBus.emit(new RewindEvent());
  }

  private void handleStop (ActionEvent aEvent) {
    this.eventBus.emit(new StopEvent());
  }

  private void handleVolumeDown (ActionEvent aEvent) {
    this.eventBus.emit(new VolumeDownEvent());
  }

  private void handleVolumeUp (ActionEvent aEvent) {
    this.eventBus.emit(new VolumeUpEvent());
  }
}
