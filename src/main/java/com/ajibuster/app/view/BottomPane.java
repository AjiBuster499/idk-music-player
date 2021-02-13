package com.ajibuster.app.view;

import com.ajibuster.app.eventbus.Event;
import com.ajibuster.app.eventbus.EventBus;
import com.ajibuster.app.eventbus.EventListener;
import com.ajibuster.app.eventbus.events.*;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BottomPane extends VBox {
  
  private HBox bottomButtons;
  private Button play, pause, stop, forward, rewind, volumeUp, volumeDown;
  private Label volume, time;
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

    this.volume = new Label("Vol: 100%");
    this.time = new Label("TI:ME");

    // TODO CLEANUP: Condense these lines?
    // TO WHOEVER READS THIS MESS:
    // It's just establishing handlers. Carry On.
    this.play.setOnAction(e -> handle(new PlayEvent()));
    this.pause.setOnAction(e -> handle(new PauseEvent()));
    this.stop.setOnAction(e -> handle(new StopEvent()));
    this.forward.setOnAction(e -> handle(new ForwardEvent()));
    this.rewind.setOnAction(e -> handle(new RewindEvent()));
    this.volumeUp.setOnAction(e -> handle(new VolumeUpEvent()));
    this.volumeDown.setOnAction(e -> handle(new VolumeDownEvent()));

    // Hopefully this doesn't grow to the nightmare in MediaHandler
    // NotLikeAji

    eventBus.listen(VolumeChangedEvent.class, new VolumeChangedEventListener());

    this.bottomButtons.getChildren().addAll(this.play, this.pause, this.stop, this.forward, this.rewind, this.volumeUp, this.volumeDown);
    this.getChildren().addAll(this.bottomButtons, this.seekBar, this.volume, this.time);

  }

  private class VolumeChangedEventListener implements EventListener<VolumeChangedEvent> {
    @Override
    public void handle(VolumeChangedEvent event) {
      String volumeFormat = "Vol: %.0f%%";
      // I thought you were smart Java
      volume.setText(String.format(volumeFormat, event.getVol() * 100));
      System.out.println(event.getVol() * 100);
    }
  }

  private void handle (Event event) {
    this.eventBus.emit(event);
  }
}
